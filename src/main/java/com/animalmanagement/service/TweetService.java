package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import org.springframework.stereotype.Service;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TweetService {

    @Autowired
    TweetMapper tweetMapper;

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
        if(tweetCensorBo.getOperate() == 0) {
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

    public Map<String, Object> getTweets(GetTweetsBo getTweetsBo) {
        int pageSize = 8;

        List<Tweet> tweetList = tweetMapper.selectByExample(new TweetExample());

        Map<String, Object> map = new HashMap<>();

        if(getTweetsBo.getType().equals("时间")) {
            tweetList.sort(Comparator.comparing(Tweet::getTime));
        } else {
            tweetList.sort(Comparator.comparing(Tweet::getViewsWeekly));
        }
        int start = (getTweetsBo.getCommentpage() - 1) * pageSize;
        if (start >= tweetList.size()) {
            map.put("users", null);
        } else {
            int end = Math.min(start + getTweetsBo.getCommentpage(), pageSize);
            map.put("users", tweetList.subList(start, end));
        }
        return map;
    }
}
