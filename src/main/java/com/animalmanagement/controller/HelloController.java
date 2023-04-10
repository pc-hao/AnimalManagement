package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public BaseResponse get() {
        return BaseResponse.builder()
                .code(1)
                .body(sysUserService.selectSysRoleByUserId(2).get(0))
                .message("Hello, this is AnimalManagement.")
                .build();
    }
}
