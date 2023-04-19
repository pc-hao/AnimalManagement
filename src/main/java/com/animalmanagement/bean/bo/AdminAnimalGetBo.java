package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AdminAnimalGetBo {
    private Integer pageNum;//每页多少项
    private Integer page;//页码
    private String  context;
}
