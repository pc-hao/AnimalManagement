package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.*;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import com.animalmanagement.enums.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnimalService {
    private final static Integer PAGE_SIZE = 10;

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
        int start = (adminAnimalGetBo.getPage() - 1) * adminAnimalGetBo.getPageNum();
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
}
