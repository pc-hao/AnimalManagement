package com.animalmanagement.config.security.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenVo {
    private Integer userId;
    private String token;
}
