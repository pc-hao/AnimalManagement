package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminAnimalContentVo {
    private String name;
    private String intro;
    private Boolean adopted;
    private List<String> avatar;
    private String maxHeightImage;
}
