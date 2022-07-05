package com.example.demo.services.Impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.response.dto.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ModelMapper modelMapper;
    private CategoryServiceImpl categoryService;
    @Mock
    private Category category;
    @Mock   
    private CategoryDTO categoryDTO;
    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
    }

    @Test
    void findAll() {
        when(categoryRepository.findAll()).thenReturn(Collections.singletonList(category));
        List<Category> categories = categoryService.findAll();

    }

    @Test
    void findAllEnabledCategories() {
    }

    @Test
    void findById() {

    }

    @Test
    void canSave() {
        Category category = new Category("Test", false);
        given(categoryRepository.save(category)).willReturn(category);
        Category savedCategory = categoryService.save(category);
        assertThat(savedCategory).isEqualTo(category);
    }

    @Test
    void existsCategoryHasThisName() {
        Category category = new Category("Test", false);
        given(categoryRepository.save(category)).willReturn(category);
        Category x =  categoryService.save(category);
        System.out.println(x);
        given(categoryService.save(category)).willThrow(new BadRequestException("Category already exists"));
    }

    @Test
    void update() {
        Category category = new Category("Test", false);
        given(categoryRepository.save(category)).willReturn(category);
        Category x =  categoryService.save(category);
        System.out.println(x);
//        Category categoryToUpdate = new Category("Test", false);
//        categoryService.update(id, categoryToUpdate);
//        assertThat(categoryService.findById(id)).isEqualTo(categoryToUpdate);
    }

    @Test
    void delete() {
    }
}