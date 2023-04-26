package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.service.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    @Autowired
    CommentService commentService;

    @PostMapping("/content")
    public BaseResponse getTweetContent(@RequestBody TweetContentBo tweetContentBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(tweetService.getTweetContent(tweetContentBo))
                .build();
    }

    @PostMapping("/getComments")
    public BaseResponse getComments(@RequestBody UserGetCommentsBo getCommentsBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(commentService.getComments(getCommentsBo))
                .build();
    }


    @PostMapping("/addComment")
    public BaseResponse addComment(@RequestBody AddCommentBo addCommentBo) {
        commentService.addComment(addCommentBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/get")
    public BaseResponse getTweets(@RequestBody GetTweetsBo getTweetsBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(tweetService.getTweets(getTweetsBo))
                .build();
    }

    @PostMapping("/like")
    public BaseResponse like(@RequestBody TweetLikeBo tweetLikeBo) {
        boolean isLike = tweetService.tweetLike(tweetLikeBo);
        Map<Object, Object> map = new HashMap<>();
        map.put("isLike", isLike);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(map)
                .build();
    }

    @PostMapping("/star")
    public BaseResponse star(@RequestBody TweetLikeBo tweetLikeBo) {
        boolean isLike = tweetService.tweetStar(tweetLikeBo);
        Map<Object, Object> map = new HashMap<>();
        map.put("isStar", isLike);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/create")
    public BaseResponse create(@RequestBody TweetCreateBo tweetCreateBo) {
        tweetService.tweetCreate(tweetCreateBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }
}
