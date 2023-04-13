package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.AccountService;
import com.animalmanagement.service.TweetService;
import com.animalmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    @PostMapping("/tweet/content")
    public BaseResponse getTweetContent(@RequestBody TweetContentBo tweetContentBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(tweetService.getTweetContent(tweetContentBo))
                .build();
    }
}
