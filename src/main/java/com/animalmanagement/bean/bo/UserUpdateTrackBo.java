package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateTrackBo {
    private Integer userId;
    private Integer animalId;
    private Integer location;
    private String time;
}
