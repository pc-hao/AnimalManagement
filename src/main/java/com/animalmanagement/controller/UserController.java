package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.AccountService;
import com.animalmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final static String DEFAULT_PASSWORD = "123456";

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @PostMapping("/resetPasswordVerify")
    public BaseResponse resetPassword(@RequestBody ResetPasswordBo resetPasswordBo) {
        accountService.verifyCode(resetPasswordBo.getEmail(), resetPasswordBo.getVerification());
        userService.changePasswordByEmail(resetPasswordBo.getEmail(), DEFAULT_PASSWORD);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("请求成功，密码已改为123456").build();
    }

    @PostMapping("/registerVerify")
    public BaseResponse register(@RequestBody RegisterBo registerBo) {
        accountService.verifyCode(registerBo.getEmail(), registerBo.getVerification());
        userService.register(registerBo);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("注册成功").build();
    }

    //-------------------------------------------
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

    @PostMapping("/testaaa")
    public BaseResponse test111() {
        userService.sendResetPasswordVeriEmail("1250747862@qq.com");
        // 错误的BaseResponse都放在service中使用throw来解决了，到这说明一定是正常的
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("Successfully send email").build();
    }
}
