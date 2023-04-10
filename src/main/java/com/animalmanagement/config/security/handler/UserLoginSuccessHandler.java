package com.animalmanagement.config.security.handler;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.config.JWTConfig;
import com.animalmanagement.config.security.bean.vo.TokenVo;
import com.animalmanagement.config.security.entity.SelfUserEntity;
import com.animalmanagement.config.security.utils.JWTTokenUtil;
import com.animalmanagement.config.security.utils.ResultUtil;
import com.animalmanagement.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理类
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功返回结果
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 组装JWT
        SelfUserEntity selfUserEntity = (SelfUserEntity) authentication.getPrincipal();
        String token = JWTTokenUtil.createAccessToken(selfUserEntity);
        token = JWTConfig.tokenPrefix + token;
        ResultUtil.response(response, BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(new TokenVo(selfUserEntity.getId(), token))
                .message("登录成功").build());
    }
}