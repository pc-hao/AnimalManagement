package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

@Service
public class AccountService {
    @Autowired
    VerificationMapper verificationMapper;

    @Autowired
    UserService userService;

    public void verifyCode(String email, String verificationCode) {
        if(Objects.isNull(email)) {
            throw new RuntimeException("EMAIL IS EMPTY");
        }
        VerificationExample example = new VerificationExample();
        example.createCriteria().andEmailEqualTo(email);
        Verification verifications = verificationMapper.selectOneByExample(example);
        if (Objects.isNull(verifications)) {
            throw new RuntimeException("Incorrect Verification Code");
        }
        Date now = new Date();
        long diff = now.getTime() - verifications.getStartTime().getTime(); //毫秒
        if (diff > 600 * 1000) {
            throw new RuntimeException("Verification code expired");
        }
        if (!Objects.equals(verifications.getVeriCode(), verificationCode)) {
            throw new RuntimeException("Incorrect Verification Code");
        }
    }
}
