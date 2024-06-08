package com.example.springexample.services;

import com.example.springexample.dto.News;

import java.util.Collection;

public interface CRUDService<T> {
    T getById(Long id);

    Collection<T> getAll();

    void create(T item);

    void update(Long id, News item);

    void deleteById(Long id);
}
