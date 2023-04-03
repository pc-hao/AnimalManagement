package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.example.AdminExample;
import com.animalmanagement.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @Autowired
    AdminMapper adminMapper;

    @GetMapping("")
    public BaseResponse get() {
        return BaseResponse.builder()
                .code(1)
                .body(adminMapper.selectOneByExample(new AdminExample()).getUsername())
                .Message("Hello, this is AnimalManagement.")
                .build();
    }
}
