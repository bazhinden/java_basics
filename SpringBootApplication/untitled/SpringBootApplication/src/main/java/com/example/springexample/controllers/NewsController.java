package com.example.springexample.controllers;

import com.example.springexample.dto.NewsDTO;
import com.example.springexample.services.CRUDService;
import com.example.springexample.services.NewsCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final CRUDService<NewsDTO> newsService;

    @GetMapping("/{id}")
    public NewsDTO getNewsById(@PathVariable Long id) {
        return newsService.getById(id);
    }

    @GetMapping
    public Collection<NewsDTO> getAllNews() {
        return newsService.getAll();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<NewsDTO>> getNewsByCategoryId(@PathVariable Long id) {
        List<NewsDTO> newsDTOList = newsService.getNewsByCategoryId(id);
        return ResponseEntity.ok(newsDTOList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNews(@RequestBody NewsDTO newsDTO) {
        newsService.create(newsDTO);
    }

    @PutMapping
    public void updateNews(@RequestBody NewsDTO newsDTO) {
        newsService.update(newsDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIdNews(@PathVariable Long id) {
        newsService.deleteById(id);
    }
}

