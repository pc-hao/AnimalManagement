package com.animalmanagement.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "image")
public class ImageConfig {
    public static String savePath;
    public static String frontPath;

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    public void setFrontPath(String frontPath) {
        this.frontPath = frontPath;
    }
}
