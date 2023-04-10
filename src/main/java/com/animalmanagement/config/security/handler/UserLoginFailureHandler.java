package com.animalmanagement.config.security.handler;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.config.security.utils.ResultUtil;
import com.animalmanagement.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录失败处理类
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    /**
     * 登录失败返回结果
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException) {
            log.info("【登录失败】" + exception.getMessage());
            ResultUtil.response(response, BaseResponse.builder().code(StatusEnum.USER_NOT_EXIT.getCode()).message("用户不存在").build());
        } else if (exception instanceof BadCredentialsException) {
            log.info("【登录失败】" + exception.getMessage());
            ResultUtil.response(response, BaseResponse.builder().code(StatusEnum.SPELLING_ERROR.getCode()).message("用户名或密码错误").build());
        } else if (exception instanceof LockedException) {
            log.info("【登录失败】" + exception.getMessage());
            ResultUtil.response(response, BaseResponse.builder().code(StatusEnum.FAILURE.getCode()).message("该用户已被冻结").build());
        } else {
            ResultUtil.response(response, BaseResponse.builder().code(StatusEnum.LOGIN_FAILURE.getCode()).message("登录失败").build());
        }
    }
}