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
}
