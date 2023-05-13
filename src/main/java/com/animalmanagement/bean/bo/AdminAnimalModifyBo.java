package com.animalmanagement.bean.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AdminAnimalModifyBo {
    private Integer recordId;
    private String name;
    private String intro;
    private Boolean adopted;
    private List<String> avatar;
}
