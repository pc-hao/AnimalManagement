package com.animalmanagement.enums;

public enum CensorStatusEnum {
    UNREVIEWED(0, "未审核"),
    PASS(1, "通过"),
    REJECT(2, "驳回");
    private Integer code;
    private String message;

    CensorStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
}
