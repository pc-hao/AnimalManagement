package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class tweetPreviewVo {
    private Integer id;
    private String username;
    private String time;
    private String title;
}
