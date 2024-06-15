package com.example.springexample.services;

import com.example.springexample.dto.CategoryDTO;
import com.example.springexample.dto.NewsDTO;
import com.example.springexample.entity.Category;
import com.example.springexample.repositories.CategoryRepository;
import com.example.springexample.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryCRUDService implements CRUDService<CategoryDTO> {

    private final CategoryRepository categoryRepository;
    private final NewsRepository newsRepository;

    @Override
    public CategoryDTO getById(Long id) {
        log.info("Get by Id: " + id);
        return mapToDto(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public Collection<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(CategoryDTO categoryDTO) {
        categoryRepository.save(mapToEntity(categoryDTO));
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        categoryRepository.save(mapToEntity(categoryDTO));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<NewsDTO> getNewsByCategoryId(Long categoryId) {
        log.info("Get news by category ID: " + categoryId);
        return newsRepository.findByCategoryId(categoryId)
                .stream()
                .map(NewsCRUDService::mapToDto)
                .collect(Collectors.toList());
    }

    public static Category mapToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setTitle(categoryDTO.getTitle());
        category.setNews(categoryDTO.getNews()
                .stream()
                .map(NewsCRUDService::mapToEntity)
                .toList());
        return category;
    }

    public static CategoryDTO mapToDto(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setNews(category.getNews()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList());
        return categoryDTO;
    }
}
