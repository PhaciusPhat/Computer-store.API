package com.example.demo.services;

import com.example.demo.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll();
    Category findById(UUID id);
    Category save(Category category);
    Category update(UUID id, Category category);
    void delete(UUID id);
}
