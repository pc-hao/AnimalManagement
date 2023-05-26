package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MainTweetVo {
    private String image;
    private String title;
    private Integer tweetId;
    private Integer likes;
    private Integer views;
    private String username;
    private String avatar;
    private Boolean isLike;
}
