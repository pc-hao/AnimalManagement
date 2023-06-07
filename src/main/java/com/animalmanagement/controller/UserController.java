package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.AccountService;
import com.animalmanagement.service.TweetService;
import com.animalmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        accountService.verifyCode(resetPasswordBo.getEmail(), resetPasswordBo.getVerification());
        userService.changePasswordByEmail(resetPasswordBo.getEmail(), DEFAULT_PASSWORD);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("请求成功，密码已改为123456").build();
    }

    @PostMapping("/registerVerify")
    public BaseResponse register(@RequestBody RegisterBo registerBo) {
        try {
            userService.register(registerBo);
        } catch (Exception e) {
            if(e.getMessage().equals("Username Is Empty"))
                return BaseResponse.builder().code(StatusEnum.USERNAME_EMPTY.getCode()).message("用户名为空").build();
            else if(e.getMessage().equals("Username Is Too Long"))
                return BaseResponse.builder().code(StatusEnum.USERNAME_TOO_LONG.getCode()).message("用户名过长").build();
            else if(e.getMessage().equals("Username Already Exists"))
                return BaseResponse.builder().code(StatusEnum.USERNAME_EXISTS.getCode()).message("用户名已存在").build();
            else if(e.getMessage().equals("Password Is Empty"))
                return BaseResponse.builder().code(StatusEnum.PASSWORD_EMPTY.getCode()).message("密码为空").build();
            else if(e.getMessage().equals("Password Is Not Consistent With Password Confirmation"))
                return BaseResponse.builder().code(StatusEnum.PASSWORD_NOT_CONSISTENT.getCode()).message("两次密码不一致").build();
            else if(e.getMessage().equals("Password Length Not Between 6 and 18"))
                return BaseResponse.builder().code(StatusEnum.PASSWORD_LENGTH.getCode()).message("密码长度不在6和18之间").build();
            else if(e.getMessage().equals("Email Is Empty") || e.getMessage().equals("EMAIL IS EMPTY"))
                return BaseResponse.builder().code(StatusEnum.EMAIL_EMPTY.getCode()).message("邮箱为空").build();
            else if(e.getMessage().equals("Incorrect Verification Code"))
                return BaseResponse.builder().code(StatusEnum.VERIFICATION_INCORRECT.getCode()).message("验证码不正确").build();
            else if(e.getMessage().equals("Verification code expired"))
                return BaseResponse.builder().code(StatusEnum.VERIFICATION_EXPIRED.getCode()).message("验证码已过期").build();
            else
                return BaseResponse.builder().code(StatusEnum.REGISTER_OTHER.getCode()).message("其它注册错误（debug用）").build();
        }
        
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

    @PostMapping("/message/unreadNum")
    public BaseResponse messageNum(@RequestBody MessageUnreadNumBo messageUnreadNumBo) {
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .body(userService.messageUnreadNum(messageUnreadNumBo))
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

    @PostMapping("/message/setRead")
    public BaseResponse messageSetRead(@RequestBody MessageSetReadBo messageSetReadBo) {
        userService.messageSetRead(messageSetReadBo);
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .message("")
            .build();
    }

    @PostMapping("/message/setReadAll")
    public BaseResponse messageSetReadAll(@RequestBody MessageSetReadAllBo messageSetReadAllBo) {
        userService.messageSetReadAll(messageSetReadAllBo);
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .message("")
            .build();
    }

    @PostMapping("/message/delete")
    public BaseResponse messageDelete(@RequestBody MessageDeleteBo messageDeleteBo) {
        userService.messageDelete(messageDeleteBo);
        return BaseResponse.builder()
            .code(StatusEnum.SUCCESS.getCode())
            .message("")
            .build();
    }
}
