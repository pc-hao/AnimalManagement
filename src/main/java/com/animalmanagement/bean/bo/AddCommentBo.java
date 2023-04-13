package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AddCommentBo {
    private Integer userId;
    private Integer tweetId;
    private String comment;
}
