package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.TweetContentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.*;
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

    public Map<String, Object> adminGetTweets(AdminGetTweetsBo adminGetTweetsBo) {
        List<Tweet> tweetList = tweetMapper.selectByExample(new TweetExample());

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", tweetList.size());

        tweetList.sort(Comparator.comparing(Tweet::getTime));
        int start = (adminGetTweetsBo.getPage() - 1) * adminGetTweetsBo.getPageNum();
        if (start >= tweetList.size()) {
            map.put("users", null);
        } else {
            int end = Math.min(start + adminGetTweetsBo.getPageNum(), tweetList.size());
            map.put("users", tweetList.subList(start, end));
        }
        return map;
    }

    public Map<String, Object> adminTweetGetContent(AdminTweetContentBo adminTweetContentBo) {
        Tweet tweet = tweetMapper.selectByPrimaryKey(adminTweetContentBo.getTweetId());

        Map<String, Object> map = new HashMap<>();
        map.put("userId", tweet.getUserId());
        map.put("title", tweet.getTitle());
        map.put("content", tweet.getContent());
        map.put("images", tweet.getImages());
        map.put("time", tweet.getTime());
        map.put("views", tweet.getViews());
        map.put("viewsWeekly", tweet.getViewsWeekly());
        map.put("likess", tweet.getLikes());
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
            tweet.setCensored(true);
        } else {
            tweet.setDeleted(true);
        }
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
        if (!tweet.getCensored()) {
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
            .andCensoredEqualTo(true)
            .andDeletedEqualTo(false);
        List<Tweet> tweetList = tweetMapper.selectByExample(example);

        Map<String, Object> map = new HashMap<>();

        if(getTweetsBo.getType().equals("时间")) {
            tweetList.sort(Comparator.comparing(Tweet::getTime));
        } else {
            tweetList.sort(Comparator.comparing(Tweet::getViewsWeekly));
        }
        int start = (getTweetsBo.getCommentpage() - 1) * pageSize;
        if (start >= tweetList.size()) {
            map.put("tweets", null);
        } else {
            int end = Math.min(start + getTweetsBo.getCommentpage(), pageSize);
            map.put("tweets", tweetList.subList(start, end));
        }
        return map;
    }

    public void tweetLike(TweetLikeBo tweetLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(tweetLikeBo.getUserId());
        if(Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetLikeBo.getTweetId());
        if(Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }

        TweetLikeExample example = new TweetLikeExample();
        example.createCriteria().andUserIdEqualTo(tweetLikeBo.getUserId());
        example.createCriteria().andTweetIdEqualTo(tweetLikeBo.getTweetId());
        TweetLikeKey tweetLike = tweetLikeMapper.selectOneByExample(example);
        if(Objects.isNull(tweetLike)) {
            TweetLikeKey insertTweetLike =  TweetLikeKey.builder()
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
        if(Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetLikeBo.getTweetId());
        if(Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }

        TweetLikeExample example = new TweetLikeExample();
        example.createCriteria().andUserIdEqualTo(tweetLikeBo.getUserId());
        example.createCriteria().andTweetIdEqualTo(tweetLikeBo.getTweetId());
        TweetLikeKey tweetLike = tweetLikeMapper.selectOneByExample(example);
        if(!Objects.isNull(tweetLike)) {
            tweetLikeMapper.deleteByPrimaryKey(tweetLike);
            tweet.setLikes(tweet.getLikes() - 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
        }
    }

    public void tweetStar(TweetLikeBo tweetLikeBo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(tweetLikeBo.getUserId());
        if(Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetLikeBo.getTweetId());
        if(Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }
        
        TweetStarExample example = new TweetStarExample();
        example.createCriteria().andUserIdEqualTo(tweetLikeBo.getUserId());
        example.createCriteria().andTweetIdEqualTo(tweetLikeBo.getTweetId());
        TweetStarKey tweetStar = tweetStarMapper.selectOneByExample(example);
        if(Objects.isNull(tweetStar)) {
            TweetStarKey insertTweetStar =  TweetStarKey.builder()
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
        if(Objects.isNull(sysUser)) {
            throw new RuntimeException("UserId Does Not Exist");
        }
        Tweet tweet = tweetMapper.selectByPrimaryKey(tweetLikeBo.getTweetId());
        if(Objects.isNull(tweet)) {
            throw new RuntimeException("TweetId Does Not Exist");
        }
        
        TweetStarExample example = new TweetStarExample();
        example.createCriteria().andUserIdEqualTo(tweetLikeBo.getUserId());
        example.createCriteria().andTweetIdEqualTo(tweetLikeBo.getTweetId());
        TweetStarKey tweetStar = tweetStarMapper.selectOneByExample(example);
        if(!Objects.isNull(tweetStar)) {
            tweetStarMapper.deleteByPrimaryKey(tweetStar);
            tweet.setStars(tweet.getStars() - 1);
            tweetMapper.updateByPrimaryKeySelective(tweet);
        }
    }
}
