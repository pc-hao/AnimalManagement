package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import com.animalmanagement.enums.*;
import com.animalmanagement.config.ImageConfig;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class TweetService {

    private static final String PICTURE_SAVE_PATH_FRONT = ImageConfig.frontPath + "/tweet/";

    private static final String PICTURE_SAVE_PATH = ImageConfig.savePath + "/tweet/";

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserService userService;

    @Autowired
    StarMapper starMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    TweetLikeMapper tweetLikeMapper;

    @Autowired
    TweetStarMapper tweetStarMapper;

    @Autowired
    TweetTagMapper tweetTagMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    MessageMapper messageMapper;

    public Map<String, Object> adminTweetGet(AdminTweetGetBo adminTweetGetBo) {
        TweetExample example = new TweetExample();
        example.createCriteria()
                .andDeletedEqualTo(false)
                .andIsHelpEqualTo(false)
                .andCensoredEqualTo(CensorStatusEnum.UNREVIEWED.getCode());

        List<Tweet> tweetList = tweetMapper.selectByExample(example);

        List<AdminTweetGetVo> voList;

        if (tweetList.isEmpty()) {
            voList = new ArrayList<>();
        } else {
            tweetList.sort(Comparator.comparing(Tweet::getTime));

            Map<Integer, UserInfo> userInfoMap = userService.getUserInfoByIdList(
                    tweetList.stream().map(Tweet::getUserId).distinct().toList());

            voList = tweetList
                    .stream()
                    .map(e -> {
                        AdminTweetGetVo vo = new AdminTweetGetVo();
                        BeanUtils.copyProperties(e, vo);
                        UserInfo userInfo = userInfoMap.get(e.getUserId());
                        vo.setUsername(userInfo.getUsername());
                        return vo;
                    }).toList();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = adminTweetGetBo.getPage() * adminTweetGetBo.getPageNum();
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + adminTweetGetBo.getPageNum(), voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> adminTweetGetContent(AdminTweetContentBo adminTweetContentBo) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(adminTweetContentBo.getTweetId());
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("Tweet ID Does Not Exist");
        }
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(tweet.getUserId());

        Map<String, Object> map = new HashMap<>();
        map.put("username", userInfo.getUsername());
        map.put("title", tweet.getTitle());
        map.put("content", tweet.getContent());
        map.put("images", tweet.getImages());
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

        if (tweet.getImages() == null) {
            map.put("images", null);
        } else {
            List<String> images = Arrays.asList(tweet.getImages().split(";"));
            map.put("images", images);
        }

        return map;
    }

    public void adminTweetCensor(TweetCensorBo tweetCensorBo) {
        Integer tweetId = tweetCensorBo.getTweetId();
        checkIdExists(tweetId);
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetId);
        Message message;
        if (tweetCensorBo.getOperate() == 0) {
            tweet.setCensored(CensorStatusEnum.PASS.getCode());
            message = Message.builder()
            .userId(tweet.getUserId())
            .content("您的贴子：“" + tweet.getTitle() +"”已通过")
            .build();
        } else {
            tweet.setCensored(CensorStatusEnum.REJECT.getCode());
            message = Message.builder()
            .userId(tweet.getUserId())
            .content("您的贴子：“" + tweet.getTitle() +"”未能通过，理由如下：\n" + tweetCensorBo.getReason())
            .build();
        }
        tweetMapper.updateByPrimaryKeySelective(tweet);
        messageMapper.insertSelective(message);
    }

    public void checkIdExists(Integer tweetId) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetId);
        if (tweet == null) {
            throw new RuntimeException("TweetId Does Not Exist");
        }
    }

    public TweetContentVo getTweetContent(TweetContentBo tweetContentBo) {
        Tweet tweet = getTweetById(tweetContentBo.getTweetId());
        UserInfo tweetAuthorInfo = userService.getUserInfoById(tweet.getUserId());
        checkTweetValid(tweetContentBo.getUserId(), tweet);

        TweetContentVo tweetContentVo = new TweetContentVo();
        BeanUtils.copyProperties(tweet, tweetContentVo);

        tweetContentVo.setTime(tweet.getTime().getYear() + "年" + tweet.getTime().getMonthValue() + "月" + tweet.getTime().getDayOfMonth() + "日 " + tweet.getTime().getHour() + ":" + tweet.getTime().getMinute());

        if (tweet.getImages() == null) {
            tweetContentVo.setImages(null);
        } else {
            List<String> images = Arrays.asList(tweet.getImages().split(";"));
            tweetContentVo.setImages(images);
        }

        tweetContentVo.setUsername(tweetAuthorInfo.getUsername());
        tweetContentVo.setAvatar(tweetAuthorInfo.getAvatar());

        TweetLikeExample likeExample = new TweetLikeExample();
        likeExample.createCriteria().andUserIdEqualTo(tweetContentBo.getUserId()).andTweetIdEqualTo(tweet.getId());
        tweetContentVo.setHasLiked(Objects.nonNull(tweetLikeMapper.selectOneByExample(likeExample)));

        TweetStarExample starExample = new TweetStarExample();
        starExample.createCriteria().andUserIdEqualTo(tweetContentBo.getUserId()).andTweetIdEqualTo(tweet.getId());
        tweetContentVo.setHasStarred(Objects.nonNull(tweetStarMapper.selectOneByExample(starExample)));

        tweetContentVo.setComments(commentService.getCommentVoListByTweetId(tweetContentBo.getTweetId()).size());

        TweetTagExample tweetTagExample = new TweetTagExample();
        tweetTagExample.createCriteria().andTweetIdEqualTo(tweetContentBo.getTweetId());
        List<TweetTagKey> tweetTagKeyList = tweetTagMapper.selectByExample(tweetTagExample);
        List<Integer> tagIdList = tweetTagKeyList.stream().map(TweetTagKey::getTagId).toList();

        if (tagIdList.isEmpty()) {
            tweetContentVo.setTags(new ArrayList<>());
        } else {
            TagExample tagExample = new TagExample();
            tagExample.createCriteria().andIdIn(tagIdList);
            List<Tag> tagList = tagMapper.selectByExample(tagExample);
            List<String> tagNameList = tagList.stream().map(Tag::getContent).toList();

            tweetContentVo.setTags(tagNameList);
        }
        return tweetContentVo;
    }

    /**
     * 通过id获取帖子，id不存在直接报错
     */
    public Tweet getTweetById(Integer id) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(id);
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("Tweet Id Does Not Exist");
        }
        return tweet;
    }

    /**
     * 校验帖子是否过审、被删除、发布
     */
    private void checkTweetValid(Integer userId, Tweet tweet) {
        if (!Objects.equals(tweet.getCensored(), CensorStatusEnum.PASS.getCode())
                && !Objects.equals(tweet.getUserId(), userId)) {
            throw new RuntimeException("Tweet Is Not Censored");
        }
        if (tweet.getDeleted()) {
            throw new RuntimeException("Tweet Has Deleted");
        }
        if (!tweet.getPublished()) {
            throw new RuntimeException("Tweet Id Does Not Exist");
        }
    }

    public Map<String, Object> getTweets(GetTweetsBo getTweetsBo) {
        int pageSize = 8;

        TweetExample example = new TweetExample();
        example.createCriteria()
                .andPublishedEqualTo(true)
                .andCensoredEqualTo(CensorStatusEnum.PASS.getCode())
                .andDeletedEqualTo(false)
                .andIsHelpEqualTo(false)
                .andTitleLike("%" + getTweetsBo.getMatch() + "%");
        List<Tweet> tweetList = tweetMapper.selectByExample(example);

        Map<String, Object> map = new HashMap<>();

        if (getTweetsBo.getType().equals("时间")) {
            tweetList.sort(Comparator.comparing(Tweet::getTime));
        } else {
            tweetList.sort(Comparator.comparing(Tweet::getViewsWeekly));
        }
        int start = getTweetsBo.getCommentpage() * pageSize;
        if (start >= tweetList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, tweetList.size());
            map.put("tweets", tweetList.subList(start, end));
        }
        return map;
    }

    public Boolean tweetLike(Integer userId, Integer tweetId) {
        userService.getUserInfoById(userId);
        Tweet tweet = getTweetById(tweetId);

        TweetLikeExample example = new TweetLikeExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andTweetIdEqualTo(tweetId);
        TweetLikeKey tweetLike = tweetLikeMapper.selectOneByExample(example);
        if (Objects.isNull(tweetLike)) {
            TweetLikeKey insertTweetLike = TweetLikeKey.builder()
                    .userId(userId)
                    .tweetId(tweetId)
                    .build();
            tweetLikeMapper.insertSelective(insertTweetLike);
            tweet.setLikes(tweet.getLikes() + 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
            return true;
        } else {
            tweetLikeMapper.deleteByPrimaryKey(tweetLike);
            tweet.setLikes(tweet.getLikes() - 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
            return false;
        }
    }

    public Boolean tweetStar(Integer userId, Integer tweetId) {
        userService.getUserInfoById(userId);
        Tweet tweet = getTweetById(tweetId);

        TweetStarExample example = new TweetStarExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andTweetIdEqualTo(tweetId);
        TweetStarKey tweetStar = tweetStarMapper.selectOneByExample(example);
        if (Objects.isNull(tweetStar)) {
            TweetStarKey insertTweetStar = TweetStarKey.builder()
                    .userId(userId)
                    .tweetId(tweetId)
                    .build();
            tweetStarMapper.insertSelective(insertTweetStar);
            tweet.setStars(tweet.getStars() + 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
            return true;
        } else {
            tweetStarMapper.deleteByPrimaryKey(tweetStar);
            tweet.setStars(tweet.getStars() - 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
            return false;
        }
    }

    public Map<String, Object> starTweet(UserStarTweetBo userStarTweetBo) {
        int pageSize = 8;

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userStarTweetBo.getUserId());
        if (sysUser == null) {
            throw new RuntimeException("User ID Does Not Exist");
        }

        TweetStarExample tweetStarExample = new TweetStarExample();
        tweetStarExample.createCriteria().andUserIdEqualTo(userStarTweetBo.getUserId());
        List<TweetStarKey> tweetStarList = tweetStarMapper.selectByExample(tweetStarExample);
        List<Integer> idList = tweetStarList.stream().map(TweetStarKey::getTweetId).distinct().toList();

        List<Tweet> tweetList;
        if (idList.isEmpty()) {
            tweetList = new ArrayList<>();
        } else {
            TweetExample tweetExample = new TweetExample();
            tweetExample.createCriteria()
                    .andIdIn(idList)
                    .andTitleLike("%" + userStarTweetBo.getContext() + "%")
                    .andIsHelpEqualTo(userStarTweetBo.getType() != 0);

            tweetList = tweetMapper.selectByExample(tweetExample);

            tweetList.sort(Comparator.comparing(Tweet::getTime));
        }

        Map<String, Object> map = new HashMap<>();

        List<UserStarTweetVo> voList = tweetList
                .stream()
                .map(e -> {
                    UserStarTweetVo vo = new UserStarTweetVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).toList();

        map.put("sumNum", voList.size());

        int start = userStarTweetBo.getPage() * pageSize;
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> selfTweet(UserSelfTweetBo userSelfTweetBo) {
        int pageSize = 8;

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userSelfTweetBo.getUserId());
        if (sysUser == null) {
            throw new RuntimeException("User ID Does Not Exist");
        }

        TweetExample tweetExample = new TweetExample();
        tweetExample.createCriteria()
                .andUserIdEqualTo(userSelfTweetBo.getUserId())
                .andIsHelpEqualTo(false)
                .andTitleLike("%" + userSelfTweetBo.getContext() + "%");

        List<Tweet> tweetList = tweetMapper.selectByExample(tweetExample);

        tweetList.sort(Comparator.comparing(Tweet::getTime));

        List<UserSelfTweetVo> voList = tweetList
                .stream()
                .map(e -> {
                    UserSelfTweetVo vo = new UserSelfTweetVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = userSelfTweetBo.getPage() * pageSize;
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> selfHelp(UserSelfHelpBo userSelfHelpBo) {
        int pageSize = userSelfHelpBo.getPageNum();

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userSelfHelpBo.getUserId());
        if (sysUser == null) {
            throw new RuntimeException("User ID Does Not Exist");
        }

        TweetExample tweetExample = new TweetExample();
        tweetExample.createCriteria()
                .andUserIdEqualTo(userSelfHelpBo.getUserId())
                .andIsHelpEqualTo(true)
                .andTitleLike("%" + userSelfHelpBo.getContext() + "%");

        List<Tweet> tweetList = tweetMapper.selectByExample(tweetExample);

        tweetList.sort(Comparator.comparing(Tweet::getTime));

        List<UserSelfHelpVo> voList = tweetList
                .stream()
                .map(e -> {
                    UserSelfHelpVo vo = new UserSelfHelpVo();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = userSelfHelpBo.getPage() * pageSize;
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> helpGet(UserHelpGetBo userHelpGetBo) {
        int pageSize = userHelpGetBo.getPageNum();

        TweetExample tweetExample = new TweetExample();
        tweetExample.createCriteria()
                .andIsHelpEqualTo(true)
                .andCensoredEqualTo(CensorStatusEnum.PASS.getCode())
                .andTitleLike("%" + userHelpGetBo.getContext() + "%");

        List<Tweet> tweetList = tweetMapper.selectByExample(tweetExample);

        if (userHelpGetBo.getType().equals("时间")) {
            tweetList.sort(Comparator.comparing(Tweet::getTime));
        } else {
            tweetList.sort(Comparator.comparing(Tweet::getViewsWeekly));
        }

        List<UserHelpGetVo> voList = tweetList
                .stream()
                .map(e -> {
                    UserHelpGetVo vo = new UserHelpGetVo();
                    BeanUtils.copyProperties(e, vo);
                    UserInfo userInfo = userInfoMapper.selectByPrimaryKey(e.getUserId());
                    vo.setUsername(userInfo.getUsername());
                    vo.setAvatar(userInfo.getAvatar());
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = userHelpGetBo.getPage() * pageSize;
        if (start >= voList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + pageSize, voList.size());
            map.put("tweets", voList.subList(start, end));
        }
        return map;
    }

    public void tweetCreate(TweetCreateBo tweetCreateBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(tweetCreateBo.getUserId());
        if (sysUser == null) {
            throw new RuntimeException("User ID Does Not Exist");
        }

        List<String> imageUrlList = tweetCreateBo.getImages();
        Integer listLength = tweetCreateBo.getImages().size();

        Tweet tweet = Tweet.builder()
                .userId(tweetCreateBo.getUserId())
                .title(tweetCreateBo.getTitle())
                .content(tweetCreateBo.getContent())
                .published(true)
                .build();
        tweetMapper.insertSelective(tweet);

        Integer id = tweet.getId();

        if (!imageUrlList.isEmpty()) {
            String images = "";
            for (int i = 0; i < listLength - 1; i++) {
                tweetCreateSaveImage(imageUrlList.get(i), tweet, i);
                String newAvatarFront = PICTURE_SAVE_PATH_FRONT + id + "_" + i + ".png";
                images += newAvatarFront;
                images += ";";
            }
            tweetCreateSaveImage(imageUrlList.get(listLength - 1), tweet, listLength - 1);
            images += PICTURE_SAVE_PATH_FRONT + id + "_" + (listLength - 1) + ".png";
            tweet.setImages(images);
            tweetMapper.updateByPrimaryKeySelective(tweet);
        }
    }

    private void tweetCreateSaveImage(String url, Tweet tweet, int num) {
        String imagePath = PICTURE_SAVE_PATH + tweet.getId() + "_" + num + ".png";
        try {
            Files.move(Paths.get(url), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteTweet(Integer userId, Integer tweetId) {
        userService.getUserInfoById(userId);
        Tweet tweet = getTweetById(tweetId);

        if(!Objects.equals(tweet.getUserId(), userId)) {
            throw new RuntimeException("不能删除他人帖子");
        }

        tweet.setDeleted(true);
        tweetMapper.updateByPrimaryKeySelective(tweet);
    }
}
