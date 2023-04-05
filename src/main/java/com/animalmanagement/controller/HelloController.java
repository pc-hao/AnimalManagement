package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.example.AdminExample;
import com.animalmanagement.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")   // 注解指出网址
public class HelloController {
    @Autowired
    AdminMapper adminMapper;

    @GetMapping("")         // 相当于是/hello后，后面的网址
    public BaseResponse get() {
        return BaseResponse.builder()
                .code(1)
                .body(adminMapper.selectOneByExample(new AdminExample()).getUsername())
                .Message("Hello, this is AnimalManagement.")
                .build();
    }
}
