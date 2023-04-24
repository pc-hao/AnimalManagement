package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminHelpReplyBo {
    @NotNull(message = "求助帖id不能为空")
    private Integer helpId;
    @NotNull(message = "回复求助不能为空")
    private String content;
}
