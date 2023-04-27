package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.service.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    AnimalService animalService;

    @Autowired
    CommentService commentService;

    @PostMapping("/get")
    public BaseResponse animalGet(@RequestBody AnimalGetBo animalGetBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(animalService.animalGet(animalGetBo))
                .build();
    }


    @PostMapping("/ai")
    public BaseResponse animalAIDetect(@RequestBody AnimalAIBo animalAIBo) {
        return animalService.animalAIPredict(animalAIBo.getImage());
    }
}
