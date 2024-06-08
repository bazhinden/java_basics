package com.example.springexample.controllers;

import com.example.springexample.dto.News;
import com.example.springexample.services.CRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/news/")
public class NewsController {
    private final CRUDService<News> newsService;

    public NewsController(CRUDService<News> newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        News news = newsService.getById(id);
        if (news == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Новость с ID " + id + " не найдена.")
            );
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping
    public ResponseEntity<Collection<News>> getAll() {
        Collection<News> newsCollection = newsService.getAll();
        return ResponseEntity.ok(newsCollection);
    }

    @PostMapping
    public ResponseEntity<News> create(@RequestBody News news) {
        newsService.create(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody News news) {
        News existingNews = newsService.getById(id);
        if (existingNews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Новость с ID " + id + " не найдена.")
            );
        }
        newsService.update(id, news);
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        News existingNews = newsService.getById(id);
        if (existingNews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Новость с ID " + id + " не найдена.")
            );
        }
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
