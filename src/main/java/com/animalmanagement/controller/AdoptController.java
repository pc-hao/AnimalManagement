package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.UserAdoptionApplyBo;
import com.animalmanagement.bean.bo.UserIdBo;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animal/adopt")
public class AdoptController {
    @Autowired
    AdoptionService adoptionService;

    @PostMapping("/self")
    public BaseResponse getSelfAdoptions(@RequestBody UserIdBo userIdBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(adoptionService.getUserSelfAdoptions(userIdBo.getUserId())).build();
    }

    @PostMapping("/apply")
    public BaseResponse userApplyAdoption(@RequestBody UserAdoptionApplyBo adoptionApplyBo) {
        adoptionService.apply(adoptionApplyBo.getUserId(), adoptionApplyBo.getAnimalId(), adoptionApplyBo.getReason());
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("Success").build();
    }
}
