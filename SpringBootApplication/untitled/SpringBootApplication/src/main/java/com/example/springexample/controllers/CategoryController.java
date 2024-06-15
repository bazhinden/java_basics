package com.example.springexample.controllers;

import com.example.springexample.dto.CategoryDTO;
import com.example.springexample.services.CategoryCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryCRUDService categoryService;
    @GetMapping("/{id}")
    public CategoryDTO getByIdCategory(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public Collection<CategoryDTO> getAllCategory() {
        return categoryService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIdCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
