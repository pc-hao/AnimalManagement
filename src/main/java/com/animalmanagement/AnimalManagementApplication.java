package com.animalmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.animalmanagement.mapper"})
public class AnimalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalManagementApplication.class, args);
    }

}
