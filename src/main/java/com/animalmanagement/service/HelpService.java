package com.animalmanagement.service;

import com.animalmanagement.bean.bo.AdminHelpContentBo;
import com.animalmanagement.bean.bo.AdminHelpGetBo;
import com.animalmanagement.bean.bo.HelpCreateBo;
import com.animalmanagement.bean.vo.AdminHelpGetVo;
import com.animalmanagement.config.ImageConfig;
import com.animalmanagement.entity.SysUser;
import com.animalmanagement.entity.Tweet;
import com.animalmanagement.entity.UserInfo;
import com.animalmanagement.example.TweetExample;
import com.animalmanagement.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class HelpService {

    private static final String PICTURE_SAVE_PATH_FRONT = ImageConfig.frontPath + "/tweet/";

    private static final String PICTURE_SAVE_PATH = ImageConfig.savePath + "/tweet/";

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserService userService;

    @Autowired
    TweetLikeMapper likeMapper;

    @Autowired
    StarMapper starMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    TweetLikeMapper tweetLikeMapper;

    @Autowired
    TweetStarMapper tweetStarMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    public Map<String, Object> adminHelpGet(AdminHelpGetBo adminHelpGetBo) {
        TweetExample example = new TweetExample();
        example.createCriteria()
            .andDeletedEqualTo(false)
            .andIsHelpEqualTo(true)
            .andTitleLike("%" + adminHelpGetBo.getContext() + "%")
            .andCensoredEqualTo(adminHelpGetBo.getCensored())
            .andSolvedEqualTo(adminHelpGetBo.getSolved());

        List<Tweet> helpList = tweetMapper.selectByExample(example);

        List<AdminHelpGetVo> voList;

        if(helpList.isEmpty()) {
            voList = new ArrayList<>();
        } else {
            helpList.sort(Comparator.comparing(Tweet::getTime));

            Map<Integer, UserInfo> userInfoMap = userService.getUserInfoByIdList(
                    helpList.stream().map(Tweet::getUserId).distinct().toList());
    
            voList = helpList
            .stream()
            .map(e -> {
                AdminHelpGetVo vo = new AdminHelpGetVo();
                BeanUtils.copyProperties(e, vo);
                UserInfo userInfo = userInfoMap.get(e.getUserId());
                vo.setUsername(userInfo.getUsername());
                return vo;
            }).toList();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = adminHelpGetBo.getPage() * adminHelpGetBo.getPageNum();
        if (start >= voList.size()) {
            map.put("helps", null);
        } else {
            int end = Math.min(start + adminHelpGetBo.getPageNum(), voList.size());
            map.put("helps", voList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> adminHelpContent(AdminHelpContentBo adminHelpContentBo) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(adminHelpContentBo.getHelpId());
        if(Objects.isNull(tweet)) {
            throw new RuntimeException("Help ID Does Not Exist");
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(tweet.getUserId());

        Map<String, Object> map = new HashMap<>();
        map.put("username", userInfo.getUsername());
        map.put("title", tweet.getTitle());
        map.put("content", tweet.getContent());
        map.put("time", tweet.getTime());
        map.put("views", tweet.getViews());
        map.put("viewsWeekly", tweet.getViewsWeekly());
        map.put("likes", tweet.getLikes());
        map.put("stars", tweet.getStars());
        map.put("isHelp", tweet.getIsHelp());
        map.put("censored", tweet.getCensored());
        map.put("solved", tweet.getSolved());
        map.put("published", tweet.getPublished());
        map.put("deleted", tweet.getDeleted());

        if(tweet.getImages() == null) {
            map.put("images", null);
        } else {
            List<String> images = Arrays.asList(tweet.getImages().split(";"));
            map.put("images", images);
        }

        return map;
    }

    public boolean changeStatusByUserSelf(Integer userId, Integer tweetId) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetId);
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }

        if(!Objects.equals(userId, tweet.getUserId())) {
            throw new RuntimeException("不能修改他人的求助帖状态");
        }

        tweet.setSolved(!tweet.getSolved());
        tweetMapper.updateByPrimaryKeySelective(tweet);
        return tweet.getSolved();
    }

    public boolean changeStatusByAdmin(Integer tweetId) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetId);
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }
        tweet.setSolved(!tweet.getSolved());
        tweetMapper.updateByPrimaryKeySelective(tweet);
        return tweet.getSolved();
    }

    public void helpCreate(HelpCreateBo helpCreateBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(helpCreateBo.getUserId());
        if(sysUser == null) {
            throw new RuntimeException("User ID Does Not Exist");
        }

        List<String> imageUrlList = helpCreateBo.getImages();
        Integer listLength = helpCreateBo.getImages().size();

        Tweet insertHelp = Tweet.builder()
            .userId(helpCreateBo.getUserId())
            .title(helpCreateBo.getTitle())
            .content(helpCreateBo.getContent())
            .isHelp(true)
            .published(true)
            .build();
        tweetMapper.insertSelective(insertHelp);

        TweetExample helpExample = new TweetExample();
        helpExample.createCriteria().andUserIdEqualTo(helpCreateBo.getUserId());
        List<Tweet> helpList = tweetMapper.selectByExample(helpExample);
        helpList.sort(Comparator.comparing(Tweet::getTime));
        Tweet help = helpList.get(helpList.size() - 1);

        Integer id = help.getId();

        if(!imageUrlList.isEmpty()) {
            String images = "";
            for(int i = 0;i < listLength - 1;i++) {
                helpCreateSaveImage(imageUrlList.get(i), help, i);
                String newAvatarFront = PICTURE_SAVE_PATH_FRONT + id + "_" + i + ".png";
                images += newAvatarFront;
                images += ";";
            }
            helpCreateSaveImage(imageUrlList.get(listLength - 1), help, listLength - 1);
            images += PICTURE_SAVE_PATH_FRONT + id + "_" + (listLength - 1) + ".png";
            help.setImages(images);
            tweetMapper.updateByPrimaryKeySelective(help);
        }
    }

    private void helpCreateSaveImage(String url,Tweet help,int num) {
        String imagePath = PICTURE_SAVE_PATH + help.getId() + "_" + num + ".png";
        try {
            Files.move(Paths.get(url), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
