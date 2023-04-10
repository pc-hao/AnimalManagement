package com.animalmanagement.config.security.utils;

import com.alibaba.fastjson.JSON;
import com.animalmanagement.bean.BaseResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果工具类
 */
@Slf4j
@NoArgsConstructor
public class ResultUtil {

    public static void response(ServletResponse response, BaseResponse baseResponse) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(baseResponse));
        } catch (Exception e) {
            log.error("【JSON输出异常】" + e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}