package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GetTweetsBo {
    private Integer userId;
    private Integer commentpage;
    private String type;
    private String match;
}
