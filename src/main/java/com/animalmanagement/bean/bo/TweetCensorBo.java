package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TweetCensorBo {
    private Integer tweetId;
    private Integer operate;
    private String reason;
}
