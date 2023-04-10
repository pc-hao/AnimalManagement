package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public BaseResponse get() {
        return BaseResponse.builder()
                .code(1)
                .body(userService.selectSysRoleByUserId(2).get(0))
                .message("Hello, this is AnimalManagement.")
                .build();
    }
}
