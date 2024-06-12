package com.example.springexample.services;

import com.example.springexample.dto.CategoryDTO;
import com.example.springexample.dto.NewsDTO;
import com.example.springexample.entity.Category;
import com.example.springexample.entity.News;
import com.example.springexample.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryCRUDService implements CRUDService<CategoryDTO> {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDTO getById(Long id) {
        log.info("Get category by ID: " + id);
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        return mapToDto(category);
    }

    @Override
    public Collection<CategoryDTO> getAll() {
        log.info("GetAll ");
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void create(CategoryDTO categoryDTO) {
        log.info("Create " + categoryDTO.getTitle());
        Category category = mapToEntity(categoryDTO);
        categoryRepository.save(category);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        log.info("Update " + categoryDTO.getTitle());
        Category category = mapToEntity(categoryDTO);
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete " + id);
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDto(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setNews(category.getNews()
                .stream()
                .map(this::mapNewsToDto)
                .collect(Collectors.toList()));
        return categoryDTO;
    }

    private Category mapToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setTitle(categoryDTO.getTitle());
        category.setNews(categoryDTO.getNews()
                .stream()
                .map(this::mapNewsToEntity)
                .collect(Collectors.toList()));
        return category;
    }

    private NewsDTO mapNewsToDto(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setText(news.getText());
        newsDTO.setDate(news.getDate());
        newsDTO.setCategoryId(news.getCategory().getId());
        return newsDTO;
    }

    private News mapNewsToEntity(NewsDTO newsDTO) {
        News news = new News();
        news.setId(newsDTO.getId());
        news.setTitle(newsDTO.getTitle());
        news.setText(newsDTO.getText());
        news.setDate(newsDTO.getDate());
        Category category = categoryRepository.findById(newsDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found for id: " + newsDTO.getCategoryId()));
        news.setCategory(category);
        return news;
    }
}
