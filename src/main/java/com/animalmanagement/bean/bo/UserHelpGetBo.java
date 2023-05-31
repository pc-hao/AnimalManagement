package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserHelpGetBo {
    private Integer pageNum;
    private Integer page;
    private String context;
    private String type;
    private String tag;
}
