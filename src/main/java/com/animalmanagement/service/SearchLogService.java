package com.animalmanagement.service;


import com.animalmanagement.entity.SearchLog;
import com.animalmanagement.example.SearchLogExample;
import com.animalmanagement.mapper.SearchLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SearchLogService {
    @Autowired
    SearchLogMapper searchLogMapper;

    public void insertSearchLog(String context, boolean isHelp) {
        if (Objects.isNull(context) || context.isEmpty()) {
            return;
        }
        if (context.length() > 512) {
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
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(num)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<String> getSelf(boolean isHelp) {
        SearchLogExample example = new SearchLogExample();
        example.createCriteria().andIsHelpEqualTo(isHelp).andUserIdEqualTo(UserService.getNowUserId());
        return searchLogMapper.selectByExample(example).stream()
                .sorted((o1, o2) -> o2.getTime().compareTo(o1.getTime()))
                .map(SearchLog::getContext)
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }
}
