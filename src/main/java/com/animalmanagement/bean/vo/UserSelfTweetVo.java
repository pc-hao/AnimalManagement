package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSelfTweetVo {
    private Integer id;
    private String images;
    private String title;
    private Integer views;
    private Integer likes;
    private Integer comments;
}
