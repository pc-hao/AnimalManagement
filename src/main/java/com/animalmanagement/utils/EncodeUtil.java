package com.animalmanagement.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncodeUtil {
    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
