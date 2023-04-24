package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminTweetContentVo {
    private String username;
	private String title;
    private String content;
    private String images;
	private LocalDateTime time;
	private Integer views;
	private Integer viewsWeekly;
	private Integer likes;
	private Integer stars;
    private Boolean isHelp;
	private Integer comments;
	private Boolean solved;
	private Integer censored;
}
