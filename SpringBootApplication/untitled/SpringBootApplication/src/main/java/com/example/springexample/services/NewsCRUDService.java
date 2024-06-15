package com.example.springexample.services;

import com.example.springexample.dto.NewsDTO;
import com.example.springexample.entity.Category;
import com.example.springexample.entity.News;
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
public class NewsCRUDService implements CRUDService<NewsDTO> {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public NewsDTO getById(Long id) {
        log.info("Get by ID: " + id);
        News news = newsRepository.findById(id).orElseThrow();
        return mapToDto(news);
    }

    @Override
    public Collection<NewsDTO> getAll() {
        log.info("Get all: ");
        return newsRepository.findAll()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(NewsDTO newsDTO) {
        log.info("Create: ");
        News news = mapToEntity(newsDTO);
        Long categoryId = newsDTO.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        news.setCategory(category);
        newsRepository.save(news);
    }

    @Override
    public void update(NewsDTO newsDTO) {
        log.info("Update: ");
        News news = mapToEntity(newsDTO);
        Long categoryId = newsDTO.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        news.setCategory(category);
        newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete: " + id);
        newsRepository.deleteById(id);
    }

    @Override
    public List<NewsDTO> getNewsByCategoryId(Long categoryId) {
        log.info("Get news by category ID: " + categoryId);
        return newsRepository.findByCategoryId(categoryId)
                .stream()
                .map(NewsCRUDService::mapToDto)
                .collect(Collectors.toList());
    }


    public static NewsDTO mapToDto(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setText(news.getText());
        newsDTO.setDate(news.getDate());
        newsDTO.setCategoryId(news.getCategory().getId());
        return newsDTO;
    }

    public static News mapToEntity(NewsDTO newsDTO) {
        News news = new News();
        news.setId(newsDTO.getId());
        news.setTitle(newsDTO.getTitle());
        news.setText(newsDTO.getText());
        news.setDate(newsDTO.getDate());
        return news;
    }
}
