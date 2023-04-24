package com.animalmanagement.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminAdoptionGetVo {
    private Integer id;
    private String username;
    private String aniname;
    private String reason;
    private LocalDateTime time;
}
