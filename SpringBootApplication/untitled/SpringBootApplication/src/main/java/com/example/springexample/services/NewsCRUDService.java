package com.example.springexample.services;

import com.example.springexample.dto.News;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NewsCRUDService implements CRUDService<News> {
    private final ConcurrentHashMap<Long, News> storage = new ConcurrentHashMap<>();

    @Override
    public News getById(Long id) {
        System.out.println("Get by ID: " + id);
        return storage.get(id);
    }

    @Override
    public Collection<News> getAll() {
        System.out.println("Get all");
        return storage.values();
    }

    @Override
    public void create(News item) {
        System.out.println("Create");
        Long nextID = storage.isEmpty() ? 1L : Collections.max(storage.keySet()) + 1;
        item.setId(nextID);
        if (item.getDate() == null) {
            item.setDate(Instant.now());
        }
        storage.put(nextID, item);
    }

    @Override
    public void update(Long id, News item) {
        System.out.println("Update " + id);
        News existingNews = storage.get(id);
        if (existingNews == null) {
            return;
        }
        item.setId(id);
        if (item.getDate() == null) {
            item.setDate(existingNews.getDate());
        }
        storage.put(id, item);
    }

    @Override
    public void deleteById(Long id) {
        System.out.println("Delete " + id);
        storage.remove(id);
    }
}
