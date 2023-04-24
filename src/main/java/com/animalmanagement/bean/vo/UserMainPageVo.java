package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserMainPageVo {
    private String username;
    private String avatarUrl;
    private String bio;
    private String phone;
}
