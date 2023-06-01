package com.animalmanagement.service;


import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Service
public class ImageService {
    @SneakyThrows
    public String imagesMaxHeight(String images) {
        int maxHeight = 0;
        String maxHeightImage = "";
        List<String> imagePathList = List.of(images.split(";"));
        for(String path: imagePathList) {
            // 文件对象
            File file = new File("/root/AnimalManagement/src/main/resources" + path);
            // 图片对象
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
            int height = bufferedImage.getHeight();
            if(height > maxHeight) {
                maxHeight = height;
                maxHeightImage = path;
            }
        }
        return maxHeightImage;
    }
}
