package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.service.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
public class UserController {
    private final static String DEFAULT_PASSWORD = "123456";

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    @PostMapping("/resetPasswordVerify")
    public BaseResponse resetPassword(@RequestBody ResetPasswordBo resetPasswordBo) {
        accountService.verifyCode(resetPasswordBo.getEmail(), DEFAULT_PASSWORD);
        userService.changePasswordByEmail(resetPasswordBo.getEmail(), resetPasswordBo.getVerification());
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("请求成功，密码已改为123456").build();
    }

    @PostMapping("/registerVerify")
    public BaseResponse register(@RequestBody RegisterBo registerBo) {
        accountService.verifyCode(registerBo.getEmail(), registerBo.getVerification());
        userService.register(registerBo);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("注册成功").build();
    }

    // 请求重置密码，邮件发验证码
    @PostMapping("/resetPasswordRequest")
    public BaseResponse resetPasswordRequest(@RequestBody ResetPasswordRequestBo resetPasswordRequestBo) {
        userService.sendResetPasswordVeriEmail(resetPasswordRequestBo.getEmail());
        // 错误的BaseResponse都放在service中使用throw来解决了，到这说明一定是正常的
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("Successfully send email").build();
    }

    // 注册时，邮件发验证码
    @PostMapping("/registerRequest")
    public BaseResponse registerRequest(@RequestBody RegisterRequestBo registerRequestBo) {
        userService.sendRegisterVeriEmail(registerRequestBo.getEmail());
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("验证码已发送").build();
    }

    @PostMapping("/modify")
    public BaseResponse modifyUserInfo(@RequestBody ModifyUserInfoBo modifyUserInfoBo) {
        userService.modifyUserInfo(modifyUserInfoBo);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("修改成功").build();
    }

    @PostMapping("/mainPage")
    public BaseResponse mainPage(@RequestBody UserMainPageBo userMainPageBo) {
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .body(userService.mainPage(userMainPageBo))
            .message("")
            .build();
    }

    @PostMapping("/starTweet")
    public BaseResponse starTweet(@RequestBody UserStarTweetBo userStarTweetBo) {
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .body(tweetService.starTweet(userStarTweetBo))
            .message("")
            .build();
    }

    @PostMapping("/selfTweet")
    public BaseResponse selfTweet(@RequestBody UserSelfTweetBo userSelfTweetBo) {
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .body(tweetService.selfTweet(userSelfTweetBo))
            .message("")
            .build();
    }

    @PostMapping("/selfHelp")
    public BaseResponse selfHelp(@RequestBody UserSelfHelpBo userSelfHelpBo) {
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .body(tweetService.selfHelp(userSelfHelpBo))
            .message("")
            .build();
    }

    @PostMapping("/help/get")
    public BaseResponse helpGet(@RequestBody UserHelpGetBo userHelpGetBo) {
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .body(tweetService.helpGet(userHelpGetBo))
            .message("")
            .build();
    }

    @PostMapping("/message/num")
    public BaseResponse messageNum(@RequestBody MessageNumBo messageNumBo) {
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .body(userService.messageNum(messageNumBo))
            .message("")
            .build();
    }

    @PostMapping("/message/get")
    public BaseResponse messageGet(@RequestBody MessageGetBo messageGetBo) {
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .body(userService.messageGet(messageGetBo))
            .message("")
            .build();
    }
}
