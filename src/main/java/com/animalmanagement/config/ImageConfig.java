package com.animalmanagement.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "image")
public class ImageConfig {
    public static String savePath;
    public static String frontPath;
    public static String USER_PICTURE_SAVE_PATH;
    public static String USER_PICTURE_SAVE_PATH_FRONT;

    public void setSavePath(String savePath) {
        this.savePath = savePath;
        this.USER_PICTURE_SAVE_PATH = savePath + "/tweet/";
    }
    public void setFrontPath(String frontPath) {
        this.frontPath = frontPath;
        this.USER_PICTURE_SAVE_PATH_FRONT = frontPath + "/tweet/";
    }
}
