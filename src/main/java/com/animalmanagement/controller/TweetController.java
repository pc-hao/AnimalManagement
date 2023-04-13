package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    TweetService tweetService;

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
}
