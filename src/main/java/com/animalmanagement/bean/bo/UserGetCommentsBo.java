package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGetCommentsBo {
    private Integer userId;
    private Integer tweetId;
    private Integer commentPage;
}
