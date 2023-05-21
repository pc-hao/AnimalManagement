package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentVo {
    private Integer id;
    private Boolean isAdmin;
    private Integer tweetId;
    private Integer userId;
    private String username;
    private String avatar;
    private String content;
    private LocalDateTime time;
    private Integer likeNum;
    private Boolean isLike;
}
