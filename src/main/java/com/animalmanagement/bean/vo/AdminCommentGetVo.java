package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminCommentGetVo implements Serializable {
    private Integer id;
    private String username;
    private LocalDateTime time;
    private String content;
}
