package com.animalmanagement.service;

import com.animalmanagement.bean.bo.AdminGetTweetsBo;
import com.animalmanagement.bean.bo.AdminGetCommentsBo;
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

    @Autowired
    CommentMapper commentMapper;

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

    public Map<String, Object> adminGetComments(AdminGetCommentsBo adminGetCommentsBo) {
        List<Comment> commentList = commentMapper.selectByExample(new CommentExample());

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", commentList.size());

        commentList.sort(Comparator.comparing(Comment::getTime));
        int start = (adminGetCommentsBo.getPage() - 1) * adminGetCommentsBo.getPageNum();
        if (start >= commentList.size()) {
            map.put("users", null);
        } else {
            int end = Math.min(start + adminGetCommentsBo.getPageNum(), commentList.size());
            map.put("users", commentList.subList(start, end));
        }
        return map;
    }
}
