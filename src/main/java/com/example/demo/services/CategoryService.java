package com.example.demo.services;

import com.example.demo.models.Category;
import com.example.demo.response.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll();
    List<CategoryDTO> findAllEnabledCategories();
    Category findById(UUID id);
    Category save(Category category);
    Category update(UUID id, Category category);
    void delete(UUID id);
}
