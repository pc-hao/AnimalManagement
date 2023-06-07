package com.animalmanagement.config.security.handler;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.config.security.utils.ResultUtil;
import com.animalmanagement.enums.StatusEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登出成功处理类
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * jwt是一次签发永久有效
     * 用户登出返回结果
     * 这里应该让前端清除掉Token
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityContextHolder.clearContext();
        ResultUtil.response(response, BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("登出成功").build());
    }
}