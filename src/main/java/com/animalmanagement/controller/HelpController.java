package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.UserAndTweetIdBo;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.HelpService;
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
        helpService.tweetCreate(tweetCreateBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

}
