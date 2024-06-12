package com.example.springexample.services;

import com.example.springexample.dto.NewsDTO;
import com.example.springexample.entity.Category;
import com.example.springexample.entity.News;
import com.example.springexample.repositories.NewsRepository;
import com.example.springexample.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class NewsCRUDService implements CRUDService<NewsDTO> {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public NewsDTO getById(Long id) {
        log.info("Get" + id);
        News news = newsRepository.findById(id).orElseThrow();
        return mapToDto(news);
    }

    @Override
    public Collection<NewsDTO> getAll() {
        log.info("GetAll");
        return newsRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void create(NewsDTO newsDTO) {
        log.info("Create " + newsDTO.getTitle());
        News news = mapToEntity(newsDTO);
        newsRepository.save(news);
    }

    @Override
    public void update(NewsDTO newsDTO) {
        log.info("Update " + newsDTO.getId());
        News news = mapToEntity(newsDTO);
        newsRepository.save(news);
    }



    @Override
    public void deleteById(Long id) {
        log.info("Delete " + id);
        newsRepository.deleteById(id);
    }

    public NewsDTO mapToDto(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setText(news.getText());
        newsDTO.setDate(news.getDate());
        newsDTO.setCategoryId(news.getCategory() != null ? news.getCategory().getId() : null);
        return newsDTO;
    }

    public News mapToEntity(NewsDTO newsDTO) {
        News news = new News();
        news.setId(newsDTO.getId());
        news.setTitle(newsDTO.getTitle());
        news.setText(newsDTO.getText());
        news.setDate(newsDTO.getDate());

        Long categoryId = newsDTO.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found for id: " + categoryId));
        news.setCategory(category);

        return news;
    }

    public Collection<NewsDTO> getByCategoryId(Long categoryId) {
        log.info("Get news by category ID: " + categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found for id: " + categoryId));
        return category.getNews()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

}
