package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSelfHelpBo {
    private Integer userId;
    private Integer page;
    private Integer pageNum;
    private String context;
    private String tag;
}
