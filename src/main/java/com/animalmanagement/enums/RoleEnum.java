package com.animalmanagement.enums;

public enum RoleEnum {
    ADMIN(1, "ADMIN"),
    USER(2, "USER");


    private Integer code;
    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
}
