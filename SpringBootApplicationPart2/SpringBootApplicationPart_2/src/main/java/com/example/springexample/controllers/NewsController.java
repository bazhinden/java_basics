package com.example.springexample.controllers;

import com.example.springexample.dto.NewsDTO;
import com.example.springexample.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsCRUDService newsService;

    public NewsController(NewsCRUDService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        NewsDTO news = newsService.getById(id);
        if (news == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Новость с ID " + id + " не найдена.")
            );
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping
    public ResponseEntity<Collection<NewsDTO>> getAll() {
        Collection<NewsDTO> newsCollection = newsService.getAll();
        return ResponseEntity.ok(newsCollection);
    }

    @PostMapping
    public ResponseEntity<NewsDTO> create(@RequestBody NewsDTO news) {
        newsService.create(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody NewsDTO news) {
        NewsDTO existingNews = newsService.getById(id);
        if (existingNews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Новость с ID " + id + " не найдена.")
            );
        }
        news.setId(id);
        newsService.update(news);
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        NewsDTO existingNews = newsService.getById(id);
        if (existingNews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Новость с ID " + id + " не найдена.")
            );
        }
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Collection<NewsDTO>> getByCategoryId(@PathVariable Long id) {
        Collection<NewsDTO> newsCollection = newsService.getByCategoryId(id);
        return ResponseEntity.ok(newsCollection);
    }
}
