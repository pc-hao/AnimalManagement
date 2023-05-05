package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.UserIdBo;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ainmal/adopt")
public class AdoptController {
    @Autowired
    AdoptionService adoptionService;

    @PostMapping("/self")
    public BaseResponse getSelfAdoptions(UserIdBo userIdBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(adoptionService.getUserSelfAdoptions(userIdBo.getUserId())).build();
    }
}
