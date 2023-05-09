package com.animalmanagement.service;

import com.animalmanagement.bean.vo.TrackVo;
import com.animalmanagement.entity.Track;
import com.animalmanagement.example.TrackExample;
import com.animalmanagement.mapper.TrackMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {
    @Autowired
    TrackMapper trackMapper;

    @Autowired
    AnimalService animalService;

    public void update(Track track) {
        trackMapper.insertSelective(track);
    }

    public Map<String, Object> getTracksByAnimalId(Integer animalId) {
        animalService.getAnimalById(animalId);
        TrackExample example = new TrackExample();
        example.createCriteria().andAnimalIdEqualTo(animalId);
        List<TrackVo> trackVos = trackMapper.selectByExample(example)
                .stream()
                .map(this::transTrackToVo)
                .sorted(Comparator.comparing(TrackVo::getTime))
                .limit(20)
                .toList();
        Map<String, Object> result = new HashMap<>();
        result.put("tracks", trackVos);
        result.put("sumNum", trackVos.size());
        return result;
    }

    private TrackVo transTrackToVo(Track track) {
        TrackVo trackVo = new TrackVo();
        BeanUtils.copyProperties(track, trackVo);
        return trackVo;
    }
}
