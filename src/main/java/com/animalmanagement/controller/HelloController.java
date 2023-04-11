package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")   // 注解指出网址
public class HelloController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public BaseResponse get(){
        throw new RuntimeException("aaa");
    }
}
