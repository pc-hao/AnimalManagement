package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.AnimalIdBo;
import com.animalmanagement.bean.bo.UserUpdateTrackBo;
import com.animalmanagement.entity.Animal;
import com.animalmanagement.entity.Track;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.AnimalService;
import com.animalmanagement.service.TrackService;
import com.animalmanagement.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Statement;

@RestController
@RequestMapping("/animal/track")
public class TrackController {
    @Autowired
    TrackService trackService;

    @Autowired
    UserService userService;

    @Autowired
    AnimalService animalService;

    @PostMapping("/update")
    public BaseResponse userUpdateTrack(@RequestBody UserUpdateTrackBo userUpdateTrackBo) {
        userService.getUserInfoById(userUpdateTrackBo.getUserId());
        animalService.getAnimalById(userUpdateTrackBo.getAnimalId());
        Track track = new Track();
        BeanUtils.copyProperties(userUpdateTrackBo, track);
        track.setTime(Date.valueOf(userUpdateTrackBo.getTime())); //TODO 不确定前端传过来的时间的格式是否可以这样操作
        trackService.update(track);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("Success").build();
    }

    @PostMapping("/get")
    public BaseResponse getTrack(@RequestBody AnimalIdBo animalIdBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(trackService.getTracksByAnimalId(animalIdBo.getAnimalId())).build();
    }
}
