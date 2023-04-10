package com.animalmanagement.config.security.handler;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.config.security.utils.ResultUtil;
import com.animalmanagement.enums.StatusEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 暂无权限处理类
 */
@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 暂无权限返回结果
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        ResultUtil.response(response, BaseResponse.builder()
                .code(StatusEnum.UNAUTHORIZED.getCode())
                .message("未授权").build());
    }
}