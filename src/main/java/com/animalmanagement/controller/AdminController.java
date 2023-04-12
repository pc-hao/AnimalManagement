package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.entity.UserInfo;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.TweetService;
import com.animalmanagement.service.UserService;
import com.animalmanagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    // @Autowired
    // UserService userService;

    // @Autowired
    // TweetService tweetService;

    // @Autowired
    // CommentService commentService;

    // @RequestMapping("/user/get")
    // public BaseResponse getUsers(@RequestBody AdminGetUserBo adminGetUserBo) {
    //     return BaseResponse.builder()
    //             .code(StatusEnum.SUCCESS.getCode())
    //             .body(userService.adminGetUsers(adminGetUserBo))
    //             .build();
    // }

    // @RequestMapping("user/black")
    // public BaseResponse changeUserStatus(@RequestBody ChangeUserStatusBo changeUserStatusBo) {
    //     userService.changeUserStatus(changeUserStatusBo);
    //     return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).build();
    // }

    // @RequestMapping("/user/modify")
    // public BaseResponse modifyUser(@RequestBody ModifyUserInfoBo modifyUserInfoBo) {
    //     userService.modifyUserInfo(modifyUserInfoBo);
    //     return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).build();
    // }

    // @PostMapping("/tweet/get")
    // public BaseResponse getTweets(@RequestBody AdminGetTweetsBo adminGetTweetsBo) {
    //     return BaseResponse.builder()
    //             .code(StatusEnum.SUCCESS.getCode())
    //             .body(tweetService.adminGetTweets(adminGetTweetsBo))
    //             .build();
    // }

    // @PostMapping("/tweet/content")
    // public BaseResponse tweetContent(@RequestBody AdminTweetContentBo adminTweetContentBo) {
    //     return BaseResponse.builder()
    //             .code(StatusEnum.SUCCESS.getCode())
    //             .body(tweetService.adminTweetGetContent(adminTweetContentBo))
    //             .build();
    // }

    // @PostMapping("/tweet/censor")
    // public BaseResponse tweetCensor(@RequestBody TweetCensorBo tweetCensorBo) {
    //     return BaseResponse.builder()
    //             .code(StatusEnum.SUCCESS.getCode())
    //             .build();
    // }

    // @PostMapping("/comment/get")
    // public BaseResponse getTweets(@RequestBody AdminGetCommentsBo adminGetCommentsBo) {
    //     return BaseResponse.builder()
    //             .code(StatusEnum.SUCCESS.getCode())
    //             .body(commentService.adminGetComments(adminGetCommentsBo))
    //             .build();
    // }
}
