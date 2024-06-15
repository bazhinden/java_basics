package com.example.springexample.dto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CategoryDTO {
    private Long id;
    private String title;
    private List<NewsDTO> news = new ArrayList<>();
}
