package com.animalmanagement.service;

import com.animalmanagement.entity.Verification;
import com.animalmanagement.example.VerificationExample;
import com.animalmanagement.mapper.VerificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class AccountService {
    @Autowired
    VerificationMapper verificationMapper;

    @Autowired
    UserService userService;

    public void verifyCode(String email, String verificationCode) {
        VerificationExample example = new VerificationExample();
        example.createCriteria().andEmailEqualTo(email);
        Verification verifications = verificationMapper.selectOneByExample(example);
        if (Objects.isNull(verifications)) {
            throw new RuntimeException("验证码过期");
        }
        Date now = new Date();
        long diff = now.getTime() - verifications.getStartTime().getTime(); //毫秒
        if (diff > 600 * 1000) {
            throw new RuntimeException("验证码过期");
        }
        if (!Objects.equals(verifications.getVeriCode(), verificationCode)) {
            throw new RuntimeException("验证码错误");
        }
    }
}
