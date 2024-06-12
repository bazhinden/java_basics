package com.example.springexample.dto;
import com.example.springexample.entity.News;
import lombok.Data;

import java.util.List;
@Data
public class CategoryDTO {
    private Long id;
    private String title;
    private List<NewsDTO> news;
}
