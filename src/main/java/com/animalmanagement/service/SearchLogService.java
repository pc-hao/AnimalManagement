package com.animalmanagement.service;


import com.animalmanagement.entity.SearchLog;
import com.animalmanagement.example.SearchLogExample;
import com.animalmanagement.mapper.SearchLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SearchLogService {
    @Autowired
    SearchLogMapper searchLogMapper;

    public void insertSearchLog(String context, boolean isHelp){
        if(context.length() > 512) {
            throw new RuntimeException("Search Word Is Too Long!");
        }
        SearchLog searchLog = SearchLog.builder()
                .context(context)
                .userId(UserService.getNowUserId())
                .isHelp(isHelp).build();
        searchLogMapper.insertSelective(searchLog);
    }

    public List<String> getHot(Integer num, boolean isHelp) {
        SearchLogExample example = new SearchLogExample();
        example.createCriteria().andIsHelpEqualTo(isHelp);
        List<SearchLog> logs = searchLogMapper.selectByExample(example);
        return logs.stream()
                .map(SearchLog::getContext)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                    .stream()
                    .sorted((o1,o2)-> o2.getValue().compareTo(o1.getValue()))
                    .limit(num)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
    }
}
