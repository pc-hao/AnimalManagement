package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.service.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        tweetService.tweetLike(tweetLikeBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/unlike")
    public BaseResponse unlike(@RequestBody TweetLikeBo tweetLikeBo) {
        tweetService.tweetUnlike(tweetLikeBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/star")
    public BaseResponse star(@RequestBody TweetLikeBo tweetLikeBo) {
        tweetService.tweetStar(tweetLikeBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/unstar")
    public BaseResponse unstar(@RequestBody TweetLikeBo tweetLikeBo) {
        tweetService.tweetUnstar(tweetLikeBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }
}
