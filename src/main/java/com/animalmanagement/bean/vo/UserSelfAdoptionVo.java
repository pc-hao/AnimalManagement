package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSelfAdoptionVo {
    private Integer id;
    private String animalName;
    private String avatar;
    private Integer censored;
    private String reason;
    private Date time;
}
