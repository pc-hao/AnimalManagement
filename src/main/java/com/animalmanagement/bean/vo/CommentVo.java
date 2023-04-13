package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentVo {
    private Integer id;
    private Integer tweetId;
    private Integer userId;
    private String username;
    private String avatar;
    private String content;
    private LocalDateTime time;
    private Integer likes;
}
