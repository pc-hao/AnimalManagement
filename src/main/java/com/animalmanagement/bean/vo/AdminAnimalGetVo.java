package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminAnimalGetVo {
    private Integer id;
    private String name;
    private String intro;
    private Boolean adopted;
    private List<String> avatar;
}
