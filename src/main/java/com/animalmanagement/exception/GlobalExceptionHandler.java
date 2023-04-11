package com.animalmanagement.exception;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.enums.StatusEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse exceptionHandler(Exception e) {
        return BaseResponse.builder()
                .code(StatusEnum.FAILURE.getCode())
                .message(e.getMessage())
                .build();
    }
}
