package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStarTweetVo {
    private Integer id;
    private String images;
    private String title;
    private Integer views;
    private Integer likes;
    private Integer comments;
    private String avatar;
    private String username;
    private Integer censored;
    private Boolean isLike;
    private Boolean solved;
}
