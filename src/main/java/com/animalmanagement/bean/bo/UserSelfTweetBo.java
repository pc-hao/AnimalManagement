package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSelfTweetBo {
    private Integer userId;
    private Integer page;
    private String context;
    private String tag;
}
