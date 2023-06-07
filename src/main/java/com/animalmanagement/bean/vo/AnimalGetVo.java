package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnimalGetVo {
    private Integer id;
    private String name;
    private String avatar;
}
