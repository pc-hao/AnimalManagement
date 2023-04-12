package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ChangeUserStatusBo {
    private Integer userId;
    private Integer operation; //1拉黑，0取消拉黑
}
