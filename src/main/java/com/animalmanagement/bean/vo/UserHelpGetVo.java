package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserHelpGetVo {
    private Integer id;
	private String title;
	private Integer views;
	private Integer likes;
	private Integer comments;
	private Boolean solved;
	private String images;
	private String username;
	private String avatar;
}
