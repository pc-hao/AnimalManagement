package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.AdminAnimalContentBo;
import com.animalmanagement.bean.bo.AnimalAIBo;
import com.animalmanagement.bean.bo.AnimalGetBo;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.AnimalService;
import com.animalmanagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/content")
    public BaseResponse animalContent(@RequestBody AdminAnimalContentBo adminAnimalContentBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(animalService.animalContent(adminAnimalContentBo))
                .build();
    }
}
