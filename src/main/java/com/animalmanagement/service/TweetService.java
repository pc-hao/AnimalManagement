package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TweetService {

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

    public Map<String, Object> adminTweetGet(AdminTweetGetBo adminTweetGetBo) {
        TweetExample example = new TweetExample();
        example.createCriteria()
            .andDeletedEqualTo(false)
            .andIsHelpEqualTo(false)
            .andCensoredEqualTo(CensorStatusEnum.UNREVIEWED.getCode());

        List<Tweet> tweetList = tweetMapper.selectByExample(example);

        List<AdminTweetGetVo> voList;

        if(tweetList.isEmpty()) {
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
        if(Objects.isNull(tweet)) {
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

        return map;
    }

    public void adminTweetCensor(TweetCensorBo tweetCensorBo) {
        Integer tweetId = tweetCensorBo.getTweetId();
        checkIdExists(tweetId);
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetId);
        if (tweetCensorBo.getOperate() == 0) {
            tweet.setCensored(CensorStatusEnum.PASS.getCode());
        } else {
            tweet.setCensored(CensorStatusEnum.REJECT.getCode());
        }
        tweetMapper.updateByPrimaryKeySelective(tweet);
    }

    public void checkIdExists(Integer tweetId) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetId);
        if (tweet == null) {
            throw new RuntimeException("TweetId Does Not Exist");
        }
    }

    public TweetContentVo getTweetContent(TweetContentBo tweetContentBo) {
        UserInfo userInfo = userService.getUserInfoById(tweetContentBo.getUserId());
        Tweet tweet = getTweetById(tweetContentBo.getTweetId());
        checkTweetValid(tweet);

        TweetContentVo tweetContentVo = new TweetContentVo();
        BeanUtils.copyProperties(tweet, tweetContentVo);
        tweetContentVo.setUsername(userInfo.getUsername());
        tweetContentVo.setAvatar(userInfo.getAvatar());

        TweetLikeExample likeExample = new TweetLikeExample();
        likeExample.createCriteria().andUserIdEqualTo(userInfo.getId()).andTweetIdEqualTo(tweet.getId());
        tweetContentVo.setHasLiked(Objects.nonNull(likeMapper.selectByExample(likeExample)));

        StarExample starExample = new StarExample();
        starExample.createCriteria().andUserIdEqualTo(userInfo.getId()).andTweetIdEqualTo(tweet.getId());
        tweetContentVo.setHasStarred(Objects.nonNull(starMapper.selectByExample(starExample)));

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
    private void checkTweetValid(Tweet tweet) {
        if (!Objects.equals(tweet.getCensored(), CensorStatusEnum.PASS.getCode())) {
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

    public void tweetLike(TweetLikeBo tweetLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(tweetLikeBo.getUserId());
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetLikeBo.getTweetId());
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }

        TweetLikeExample example = new TweetLikeExample();
        example.createCriteria()
            .andUserIdEqualTo(tweetLikeBo.getUserId())
            .andTweetIdEqualTo(tweetLikeBo.getTweetId());
        TweetLikeKey tweetLike = tweetLikeMapper.selectOneByExample(example);
        if (Objects.isNull(tweetLike)) {
            TweetLikeKey insertTweetLike = TweetLikeKey.builder()
                    .userId(tweetLikeBo.getUserId())
                    .tweetId(tweetLikeBo.getTweetId())
                    .build();
            tweetLikeMapper.insertSelective(insertTweetLike);
            tweet.setLikes(tweet.getLikes() + 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
        }
    }

    public void tweetUnlike(TweetLikeBo tweetLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(tweetLikeBo.getUserId());
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetLikeBo.getTweetId());
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }

        TweetLikeExample example = new TweetLikeExample();
        example.createCriteria()
            .andUserIdEqualTo(tweetLikeBo.getUserId())
            .andTweetIdEqualTo(tweetLikeBo.getTweetId());
        TweetLikeKey tweetLike = tweetLikeMapper.selectOneByExample(example);
        if (!Objects.isNull(tweetLike)) {
            tweetLikeMapper.deleteByPrimaryKey(tweetLike);
            tweet.setLikes(tweet.getLikes() - 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
        }
    }

    public void tweetStar(TweetLikeBo tweetLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(tweetLikeBo.getUserId());
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetLikeBo.getTweetId());
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }

        TweetStarExample example = new TweetStarExample();
        example.createCriteria()
            .andUserIdEqualTo(tweetLikeBo.getUserId())
            .andTweetIdEqualTo(tweetLikeBo.getTweetId());
        TweetStarKey tweetStar = tweetStarMapper.selectOneByExample(example);
        if (Objects.isNull(tweetStar)) {
            TweetStarKey insertTweetStar = TweetStarKey.builder()
                    .userId(tweetLikeBo.getUserId())
                    .tweetId(tweetLikeBo.getTweetId())
                    .build();
            tweetStarMapper.insertSelective(insertTweetStar);
            tweet.setStars(tweet.getStars() + 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
        }
    }

    public void tweetUnstar(TweetLikeBo tweetLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(tweetLikeBo.getUserId());
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetLikeBo.getTweetId());
        if (Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }

        TweetStarExample example = new TweetStarExample();
        example.createCriteria()
            .andUserIdEqualTo(tweetLikeBo.getUserId())
            .andTweetIdEqualTo(tweetLikeBo.getTweetId());
        TweetStarKey tweetStar = tweetStarMapper.selectOneByExample(example);
        if (!Objects.isNull(tweetStar)) {
            tweetStarMapper.deleteByPrimaryKey(tweetStar);
            tweet.setStars(tweet.getStars() - 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
        }
    }

    public Map<String, Object> starTweet(UserStarTweetBo userStarTweetBo) {
        int pageSize = 8;

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userStarTweetBo.getUserId());
        if(sysUser == null) {
            throw new RuntimeException("User ID Does Not Exist");
        }

        TweetStarExample tweetStarExample = new TweetStarExample();
        tweetStarExample.createCriteria().andUserIdEqualTo(userStarTweetBo.getUserId());
        List<TweetStarKey> tweetStarList = tweetStarMapper.selectByExample(tweetStarExample);
        List<Integer> idList = tweetStarList.stream().map(TweetStarKey::getUserId).distinct().toList();

        List<Tweet> tweetList;
        if(idList.isEmpty()) {
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
        if(sysUser == null) {
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
        if(sysUser == null) {
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
            .andTitleLike("%" + userHelpGetBo.getContext() + "%");

        List<Tweet> tweetList = tweetMapper.selectByExample(tweetExample);

        if(userHelpGetBo.getType().equals("时间")) {
            tweetList.sort(Comparator.comparing(Tweet::getTime));
        } else {
            tweetList.sort(Comparator.comparing(Tweet::getViewsWeekly));
        }

        List<UserHelpGetVo> voList = tweetList
                .stream()
                .map(e -> {
                    UserHelpGetVo vo = new UserHelpGetVo();
                    BeanUtils.copyProperties(e, vo);
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
}
