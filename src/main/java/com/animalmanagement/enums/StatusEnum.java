package com.animalmanagement.enums;

public enum StatusEnum {
    SUCCESS(0, "请求响应成功"),
    FAILURE(1, "请求响应失败"),

    NOT_LOGIN(2, "用户未登录"),
    LOGIN_FAILURE(3, "用户登录失败"),
    UNAUTHORIZED(4, "用户未授权"),
    USER_NOT_EXIT(5, "用户不存在"),
    SPELLING_ERROR(6, "用户名或密码错误");

    private Integer code;
    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
}
