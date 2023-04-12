package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.ModifyUserInfoBo;
import com.animalmanagement.bean.bo.RegisterBo;
import com.animalmanagement.bean.bo.ResetPasswordBo;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.AccountService;
import com.animalmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @PostMapping("/resetPasswordVerify")
    public BaseResponse resetPassword(@RequestBody ResetPasswordBo resetPasswordBo) {
        accountService.verifyCode(resetPasswordBo.getEmail(), resetPasswordBo.getVerification());
        userService.changePasswordByEmail(resetPasswordBo.getEmail(), resetPasswordBo.getVerification());
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("请求成功，密码已改为123456").build();
    }

    @PostMapping("/registerVerify")
    public BaseResponse register(@RequestBody RegisterBo registerBo) {
        accountService.verifyCode(registerBo.getEmail(), registerBo.getVerification());
        userService.register(registerBo);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("注册成功").build();
    }

    @PostMapping("/modify")
    public BaseResponse modifyUserInfo(@RequestBody ModifyUserInfoBo modifyUserInfoBo) {
        userService.modifyUserInfo(modifyUserInfoBo);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("修改成功").build();
    }
}
