package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.AnimalIdBo;
import com.animalmanagement.bean.bo.UserUpdateTrackBo;
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

import java.text.ParseException;

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
    public BaseResponse userUpdateTrack(@RequestBody UserUpdateTrackBo userUpdateTrackBo) throws ParseException {
        userService.getUserInfoById(userUpdateTrackBo.getUserId());
        animalService.getAnimalById(userUpdateTrackBo.getAnimalId());
        Track track = new Track();
        BeanUtils.copyProperties(userUpdateTrackBo, track);
        track.setTime(userUpdateTrackBo.getTime());
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
