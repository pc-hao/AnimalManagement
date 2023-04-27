package com.animalmanagement.bean.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class HelpCreateBo {
    private Integer userId;
    private String title;
    private String content;
    private List<String> images;
    private List<String> tags;
}
