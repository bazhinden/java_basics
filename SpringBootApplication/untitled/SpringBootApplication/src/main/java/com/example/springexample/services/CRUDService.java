package com.example.springexample.services;

import com.example.springexample.dto.NewsDTO;

import java.util.Collection;
import java.util.List;

public interface CRUDService<T> {
    T getById(Long id);

    Collection<T> getAll();

    void create(T item);

    void update(T item);

    void deleteById(Long id);
    List<NewsDTO> getNewsByCategoryId(Long categoryId);

}
