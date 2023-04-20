package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminHelpGetVo {
    private Integer id;
    private String username;
	private String title;
	private LocalDateTime time;
	private Integer views;
	private Integer viewsWeekly;
	private Integer likes;
	private Integer stars;
	private Integer comments;
	private Boolean solved;
	private Integer censored;
}
