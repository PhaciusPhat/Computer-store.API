package com.example.demo.services.Impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.response.dto.CategoryDTO;
import com.example.demo.services.CategoryService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;


    private CategoryDTO convertToDTO(Category category) {
        return category != null ? modelMapper.map(category, CategoryDTO.class) : null;
    }

    private Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    private Category findByNameExceptId(UUID id, String name) {
        return categoryRepository.findByNameExceptId(id, name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryDTO> findAllEnabledCategories() {
        return categoryRepository.findAllEnabledCategories().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public Category findById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @Override
    public Category save(Category category) {
        category.setDisabled(false);
        if (findByName(category.getName()) != null) {
            throw new BadRequestException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(UUID id, Category category) {
        if (findByNameExceptId(id, category.getName()) != null) {
            throw new BadRequestException("Category already exists");
        }
        Category categoryToUpdate = findById(id);
        categoryToUpdate.setName(category.getName());
        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public void delete(UUID id) {
        Category category = findById(id);
        category.setDisabled(!category.isDisabled());
        categoryRepository.save(category);
    }
}
