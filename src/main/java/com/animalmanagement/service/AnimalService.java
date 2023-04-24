package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.config.ImageConfig;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnimalService {
    private final static Integer PAGE_SIZE = 10;

    private final static String PICTURE_SAVE_PATH = ImageConfig.savePath + "/animal/";

    @Autowired
    AnimalMapper animalMapper;

    public Map<String, Object> adminAnimalGet(AdminAnimalGetBo adminAnimalGetBo) {
        AnimalExample example = new AnimalExample();

        if(!adminAnimalGetBo.getContext().isEmpty()) {
            example.createCriteria().andNameLike(adminAnimalGetBo.getContext());
        }

        List<Animal> animalList = animalMapper.selectByExample(example);

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", animalList.size());

        animalList.sort(Comparator.comparing(Animal::getName));
        int start = adminAnimalGetBo.getPage() * adminAnimalGetBo.getPageNum();
        if (start >= animalList.size()) {
            map.put("records", null);
        } else {
            int end = Math.min(start + adminAnimalGetBo.getPageNum(), animalList.size());
            map.put("records", animalList.subList(start, end));
        }
        return map;
    }

    public AdminAnimalContentVo adminAnimalContent(AdminAnimalContentBo adminAnimalContentBo) {
        Animal animal = animalMapper.selectByPrimaryKey(adminAnimalContentBo.getRecordId());
        if(Objects.isNull(animal)) {
            throw new RuntimeException("Animal ID Does Not Exist");
        }

        AdminAnimalContentVo vo = new AdminAnimalContentVo();
        BeanUtils.copyProperties(animal, vo);

        return vo;
    }

    public void adminAnimalModify(AdminAnimalModifyBo adminAnimalModifyBo) {
        Animal animal = animalMapper.selectByPrimaryKey(adminAnimalModifyBo.getRecordId());
        if(Objects.isNull(animal)) {
            throw new RuntimeException("Animal ID Does Not Exist");
        }

        if(!Objects.isNull(adminAnimalModifyBo.getAdopted()) && !adminAnimalModifyBo.getName().isEmpty()) {
            animal.setName(adminAnimalModifyBo.getName());
        }
        if(!Objects.isNull(adminAnimalModifyBo.getAdopted()) && !adminAnimalModifyBo.getIntro().isEmpty()) {
            animal.setIntro(adminAnimalModifyBo.getIntro());
        }
        if(!Objects.isNull(adminAnimalModifyBo.getAdopted())) {
            animal.setAdopted(adminAnimalModifyBo.getAdopted());
        }
        if (!Objects.isNull(adminAnimalModifyBo.getAvatar())) {
            String newAvatar = PICTURE_SAVE_PATH + adminAnimalModifyBo.getRecordId() + ".png";
            try {
                Files.move(Paths.get(adminAnimalModifyBo.getAvatar()), Paths.get(newAvatar), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            animal.setAvatar(newAvatar);
        }
        animalMapper.updateByPrimaryKeySelective(animal);
    }

    public void adminAnimalDelete(AdminAnimalDeleteBo adminAnimalDeleteBo) {
        Animal animal = animalMapper.selectByPrimaryKey(adminAnimalDeleteBo.getRecordId());
        if(Objects.isNull(animal)) {
            throw new RuntimeException("Animal ID Does Not Exist");
        }

        animalMapper.deleteByPrimaryKey(animal.getId());
    }
}
