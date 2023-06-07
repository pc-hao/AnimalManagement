package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageGetVo {
    private Integer id;
    private String content;
    private Boolean read;
    private String time;
}
