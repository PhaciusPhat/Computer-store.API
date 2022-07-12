package com.example.demo.services.Impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.response.dto.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.webjars.NotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJpaTest
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ModelMapper modelMapper;
    private CategoryServiceImpl categoryService;
    @Mock
    private Category category;
    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
    }

    @Test
    void findAll() {
        categoryService.findAll();
        verify(categoryRepository).findAll();
    }

    @Test
    void findAllEnabledCategories() {
        categoryService.findAllEnabledCategories();
        verify(categoryRepository).findAllEnabledCategories();
    }

    @Test
    void convertToDTO() {
        categoryService.convertToDTO(category);
        verify(modelMapper).map(category, CategoryDTO.class);
    }

    @Test
    void canFindById() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
        Category category = categoryService.findById(id);
        assertThat(category).isEqualTo(this.category);
    }

    @Test
    void canNotFindById() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> categoryService.findById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Category not found");
    }

    @Test
    void canSaveCategory() {
        Category category = new Category("Test", false);
        categoryService.save(category);
        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(category);
    }

    @Test
    void existsCategoryHasThisNameWhenAdd() {
        String name = "Test";
        Category category = new Category(name, false);
        categoryService.save(category);
        given(categoryRepository.findByName(name)).willReturn(category);
        assertThatThrownBy(() -> categoryService.save(category))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Category already exists");
    }

    @Test
    void canUpdate() {
        UUID id = UUID.randomUUID();
        Category category = new Category("Test", false);
        given(categoryRepository.findById(id)).willReturn(java.util.Optional.of(category));
        categoryService.update(id, category);
        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(category);
    }

    @Test
    void dontExistsUpdateCategory() {
        UUID id = UUID.randomUUID();
        Category category = new Category("Test", false);
        given(categoryRepository.findById(id)).willReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> categoryService.update(id, category))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Category not found");
    }

    @Test
    void existsCategoryHasThisNameWhenUpdate() {
        UUID id = UUID.randomUUID();
        Category category = new Category("Test", false);
        given(categoryRepository.findByNameExceptId(id, "Test")).willReturn(category);
        assertThatThrownBy(() -> categoryService.update(id, category))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Category already exists");
    }

    @Test
    void canDelete() {
        UUID id = UUID.randomUUID();
        given(categoryRepository.findById(id)).willReturn(java.util.Optional.of(category));
        categoryService.delete(id);
        verify(categoryRepository).save(category);
    }

    @Test
    void dontExistsDeleteCategory() {
        UUID id = UUID.randomUUID();
        given(categoryRepository.findById(id)).willReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> categoryService.delete(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Category not found");
    }
}