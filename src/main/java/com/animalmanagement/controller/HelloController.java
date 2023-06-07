package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")   // 注解指出网址
public class HelloController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public BaseResponse get(){
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).build();
    }
}
