package com.animalmanagement.enums;

public enum StatusEnum {
    SUCCESS(0, "请求响应成功"),
    FAILURE(1, "请求响应失败"),

    NOT_LOGIN(2, "用户未登录"),
    LOGIN_FAILURE(3, "用户登录失败"),
    UNAUTHORIZED(4, "用户未授权"),
    USER_NOT_EXIT(5, "用户不存在"),
    SPELLING_ERROR(6, "用户名或密码错误"),

    BLACK(7,"用户被拉黑"),

    USERNAME_EMPTY(8,"用户名为空"),
    USERNAME_TOO_LONG(9,"用户名过长"),
    USERNAME_EXISTS(10,"用户名已存在"),
    PASSWORD_EMPTY(11,"密码为空"),
    PASSWORD_NOT_CONSISTENT(12,"两次密码不一致"),
    PASSWORD_LENGTH(13,"密码长度不在6和18之间"),
    EMAIL_EMPTY(14,"邮箱为空"),
    REGISTER_OTHER(15,"其它注册错误（debug用）");

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
