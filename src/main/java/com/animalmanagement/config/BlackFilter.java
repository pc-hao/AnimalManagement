package com.animalmanagement.config;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.config.security.utils.ResultUtil;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Slf4j
@Order()
public class BlackFilter implements Filter {
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(userService.getUserInfoById(UserService.getNowUserId()).getBlacked()) {
            ResultUtil.response(servletResponse, BaseResponse.builder().code(StatusEnum.FAILURE.getCode()).message("你已被拉黑").build());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
