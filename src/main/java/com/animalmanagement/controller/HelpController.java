package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.HelpService;
import com.animalmanagement.service.SearchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/help")
public class HelpController {
    @Autowired
    HelpService helpService;

    @Autowired
    SearchLogService searchLogService;

    @PostMapping("/changeStatus")
    public BaseResponse changeStatus(@RequestBody UserAndTweetIdBo userAndTweetIdBo) {
        boolean isLike = helpService.changeStatusByUserSelf(userAndTweetIdBo.getUserId(), userAndTweetIdBo.getTweetId());
        Map<Object, Object> map = new HashMap<>();
        map.put("solved", isLike);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(map)
                .build();
    }

    @PostMapping("/create")
    public BaseResponse create(@RequestBody HelpCreateBo helpCreateBo) {
        helpService.helpCreate(helpCreateBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/hotSearch")
    public BaseResponse getHotSearch() {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(searchLogService.getHot(10, true)).build();
    }
}
