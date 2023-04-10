package com.animalmanagement.config.security.handler;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.config.security.utils.ResultUtil;
import com.animalmanagement.enums.StatusEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问无权限资源时的异常
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 用户未登录
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResultUtil.response(response, BaseResponse.builder()
                .code(StatusEnum.FAILURE.getCode())
                .message("用户未登录").build());
    }
}