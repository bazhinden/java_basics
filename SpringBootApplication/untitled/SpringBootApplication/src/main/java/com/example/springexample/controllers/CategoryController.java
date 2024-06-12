package com.example.springexample.controllers;

import com.example.springexample.dto.CategoryDTO;
import com.example.springexample.services.CategoryCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryCRUDService categoryCRUDService;

    @GetMapping
    public Collection<CategoryDTO> getAll() {
        return categoryCRUDService.getAll();
    }

    @PostMapping
    public void create(@RequestBody CategoryDTO categoryDTO) {
        categoryCRUDService.create(categoryDTO);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        categoryCRUDService.update(categoryDTO);
    }
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id) {
        categoryCRUDService.deleteById(id);
    }
}