package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ModifyUserInfoBo {
    private String userId;
    private String username;
    private String password;
    private String passwordConfirm;
    private String phone;
    private String bio;
}
