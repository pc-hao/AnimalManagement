package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.config.ImageConfig;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

import com.animalmanagement.utils.EncodeUtil;

@Service
public class AdoptionService {
    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserService userService;

    @Autowired
    TweetLikeMapper likeMapper;

    @Autowired
    StarMapper starMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    TweetLikeMapper tweetLikeMapper;

    @Autowired
    TweetStarMapper tweetStarMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    AdoptionMapper adoptionMapper;

    @Autowired
    AnimalMapper animalMapper;

    @Autowired
    MessageMapper messageMapper;

    public Map<String, Object> adminAdoptionGet(AdminAdoptionGetBo adminAdoptionGetBo) {
        AdoptionExample example = new AdoptionExample();
        example.createCriteria()
            .andCensoredEqualTo(CensorStatusEnum.UNREVIEWED.getCode());

        List<Adoption> adoptionList = adoptionMapper.selectByExample(example);

        adoptionList.sort(Comparator.comparing(Adoption::getUserId));

        List<AdminAdoptionGetVo> voList = adoptionList
                .stream()
                .map(e -> {
                    AdminAdoptionGetVo vo = new AdminAdoptionGetVo();
                    BeanUtils.copyProperties(e, vo);
                    UserInfo userInfo = userInfoMapper.selectByPrimaryKey(e.getUserId());
                    Animal animal = animalMapper.selectByPrimaryKey(e.getAnimalId());
                    vo.setUsername(userInfo.getUsername());
                    vo.setAniname(animal.getName());
                    return vo;
                }).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", voList.size());

        int start = adminAdoptionGetBo.getPage() * adminAdoptionGetBo.getPageNum();
        if (start >= voList.size()) {
            map.put("adoptions", null);
        } else {
            int end = Math.min(start + adminAdoptionGetBo.getPageNum(), voList.size());
            map.put("adoptions", voList.subList(start, end));
        }
        return map;
    }

    public void adminAdoptionCensor(AdminAdoptionCensorBo adminAdoptionCensorBo) {
        Adoption adoption = adoptionMapper.selectByPrimaryKey(adminAdoptionCensorBo.getAdoptionId());
        if(Objects.isNull(adoption)) {
            throw new RuntimeException("Adoption ID Does Not Exist");
        }

        Message message;

        if(adminAdoptionCensorBo.getOperate() == 0) {
            adoption.setCensored(CensorStatusEnum.PASS.getCode());
            message = Message.builder()
            .userId(adoption.getUserId())
            .content("您的领养申请已通过")
            .build();
        } else {
            adoption.setCensored(CensorStatusEnum.REJECT.getCode());
            message = Message.builder()
            .userId(adoption.getUserId())
            .content("您的领养申请未能通过，理由如下：\n" + adminAdoptionCensorBo.getReason())
            .build();
        }

        messageMapper.insertSelective(message);

        adoptionMapper.updateByPrimaryKeySelective(adoption);
    }
}
