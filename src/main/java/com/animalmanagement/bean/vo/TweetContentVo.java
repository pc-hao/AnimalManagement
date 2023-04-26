package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TweetContentVo {
    private String title;
    private String content;
    private String images;
    private LocalDateTime time;
    private Integer views;
    private Integer viewsWeekly;

    private Integer userId;
    private String username; //需要单独查询
    private String avatar; //需要单独查询

    private Integer likes;
    private Integer stars;
    private Integer comments;

    private Boolean hasLiked; //需要单独查询
    private Boolean hasStarred; //需要单独查询

    private Boolean isHelp;
    private Boolean solved;

    private List<String> tags;
}
