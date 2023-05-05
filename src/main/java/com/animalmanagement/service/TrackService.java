package com.animalmanagement.service;

import com.animalmanagement.entity.Track;
import com.animalmanagement.mapper.TrackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
    @Autowired
    TrackMapper trackMapper;

    public void update(Track track) {
        trackMapper.insertSelective(track);
    }
}
