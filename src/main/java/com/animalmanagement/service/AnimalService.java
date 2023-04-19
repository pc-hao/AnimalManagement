package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.CommentVo;
import com.animalmanagement.enums.CensorStatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;

import org.springframework.stereotype.Service;

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
}
