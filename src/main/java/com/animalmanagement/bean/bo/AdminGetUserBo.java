package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AdminGetUserBo {
    private Integer pageNum;//每页多少项
    private Integer page;//页码
    private String context;//搜索关键字，不搜索时为空串""
    private Boolean isBlack;//只返回被拉黑的用户
}
