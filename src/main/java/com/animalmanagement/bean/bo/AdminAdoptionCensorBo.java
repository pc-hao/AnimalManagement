package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AdminAdoptionCensorBo {
    private Integer adoptionId;
    private Integer operate;
    private String reason;
}
