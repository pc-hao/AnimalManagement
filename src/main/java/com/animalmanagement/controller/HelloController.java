package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("")
    public BaseResponse get() {
        return BaseResponse.builder()
                .code(1)
                .message("Hello, this is AnimalManagement.")
                .build();
    }
}
