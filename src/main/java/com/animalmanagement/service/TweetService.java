package com.animalmanagement.service;

import com.animalmanagement.bean.bo.AdminGetTweetsBo;
import com.animalmanagement.bean.bo.AdminGetCommentsBo;
import org.springframework.stereotype.Service;
import com.animalmanagement.entity.Comment;
import com.animalmanagement.mapper.CommentMapper;
import com.animalmanagement.example.CommentExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TweetService {

    @Autowired
    CommentMapper commentMapper;

    public Map<String, Object> adminGetTweets(AdminGetTweetsBo adminGetTweetsBo) {
        // List<Comment> commentList = commentMapper.selectByExample(new CommentExample());

        // Map<String, Object> map = new HashMap<>();
        // map.put("sumNum", commentList.size());

        // commentList.sort(Comparator.comparing(Comment::get));
        // int start = adminGetUserBo.getPage() * adminGetUserBo.getPageNum();
        // if (start >= userList.size()) {
        //     map.put("users", null);
        // } else {
        //     int end = Math.min(start + adminGetUserBo.getPageNum(), userList.size());
        //     map.put("users", userList.subList(start, end));
        // }
        // return map;
        return null;
    }

    public Map<String, Object> adminGetComments(AdminGetCommentsBo adminGetCommentsBo) {
        // List<Comment> commentList = commentMapper.selectByExample(new CommentExample());

        // if (Objects.nonNull(adminGetUserBo.getContext())) {
        //     userList = userList.stream()
        //             .filter(e ->
        //                     e.getUsername().contains(adminGetUserBo.getContext()))
        //             .collect(Collectors.toList());
        // }

        // Map<String, Object> map = new HashMap<>();
        // map.put("sumNum", userList.size());

        // userList.sort(Comparator.comparingInt(UserInfo::getId));
        // int start = adminGetUserBo.getPage() * adminGetUserBo.getPageNum();
        // if (start >= userList.size()) {
        //     map.put("users", null);
        // } else {
        //     int end = Math.min(start + adminGetUserBo.getPageNum(), userList.size());
        //     map.put("users", userList.subList(start, end));
        // }
        // return map;
        return null;
    }
}
