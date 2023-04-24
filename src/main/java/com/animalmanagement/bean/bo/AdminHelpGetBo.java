package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AdminHelpGetBo {
    private Integer pageNum;//每页多少项
    private Integer page;//页码
    private String context;//搜索关键字，不搜索时为空串""
    private Integer censored; //0未审核，1通过，2驳回
	private Boolean solved; //false未解决，true已解决
}
