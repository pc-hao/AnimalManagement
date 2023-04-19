package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AdminAnimalDeleteBo {
    private Integer recordId;
}
