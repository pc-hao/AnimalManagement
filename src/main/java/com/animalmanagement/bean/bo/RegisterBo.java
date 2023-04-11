package com.animalmanagement.bean.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RegisterBo {
    private String username;
    private String password;
    private String passwordConfirm;
    private String phone;
    private String email;
    private String verification;
}
