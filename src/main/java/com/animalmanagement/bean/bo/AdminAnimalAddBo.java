package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class AdminAnimalAddBo {
    @NotNull(message = "动物名不能为空")
    private String name;
    @NotNull(message = "动物介绍不能为空")
    private String intro;
    @NotNull(message = "领养与否不能为空")
    private Boolean adopted;
    private List<String> avatar;
}
