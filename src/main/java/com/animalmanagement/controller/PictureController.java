package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.config.ImageConfig;
import com.animalmanagement.enums.StatusEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.management.RuntimeErrorException;

@RestController
@RequestMapping("/image")
public class PictureController {
    private static final List<String> suffixNameList = Arrays.asList(".jpg", ".jpeg", ".png");

    @PostMapping("/upload")
    public BaseResponse profilePhotoUpload(@RequestParam("image") MultipartFile fileUpload, @RequestParam("type") String fileType) throws IOException {
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        String frontFilePath;
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if (!suffixNameList.stream().anyMatch(e -> e.equals(suffixName))) {
            throw new RuntimeException("Image Format Is Illegal!");
        }
        //为了避免发生图片替换，这里使用了文件名重新生成
        fileName = UUID.randomUUID() + suffixName;
        String filePath = null;
        if ("animal".equals(fileType)) {
            filePath = ImageConfig.savePath + "/animal/temp/" + fileName;
            frontFilePath = "/images/animal/temp/" + fileName;
        } else if ("tweet".equals(fileType) || "help".equals(fileType)) {
            filePath = ImageConfig.savePath + "/tweet/temp/" + fileName;
            frontFilePath = "/images/tweet/temp/" + fileName;
        } else if ("user".equals(fileType)) {
            filePath = ImageConfig.savePath + "/user/temp/" + fileName;
            frontFilePath = "/images/user/temp/" + fileName;
        } else {
            throw new RuntimeException("Invalid type");
        }
        if (Objects.isNull(filePath)) {
            throw new RuntimeException("Image Type Is Illegal!");
        }
        //将图片保存到文件夹里
        fileUpload.transferTo(new File(filePath));

        HashMap<String, String> result = new HashMap<>();
        result.put("imagePath", frontFilePath);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).body(result).build();
    }
}
