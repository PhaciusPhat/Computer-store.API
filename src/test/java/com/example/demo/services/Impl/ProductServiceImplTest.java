package com.example.demo.services.Impl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.BrandService;
import com.example.demo.services.CategoryService;
import com.example.demo.services.CloudinaryService;
import com.example.demo.services.ProductImagesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@DataJpaTest
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CloudinaryService cloudinaryService;
    @Mock
    private BrandService brandService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ProductImagesService productImagesService;
    private ProductServiceImpl productService;
    @Mock
    Pageable pageable;
    @Mock
    private Product product;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, cloudinaryService, brandService, categoryService, productImagesService);
    }

    @Test
    void findAll() {
        productService.findAll(pageable);
        verify(productRepository).findAll(pageable);
    }

    @Test
    void findAllByCategoryId() {
        UUID categoryId = UUID.randomUUID();
        productService.findAllByCategoryId(categoryId, pageable);
        verify(productRepository).findAllByCategoryId(categoryId, pageable);
    }

    @Test
    void findAllByBrandId() {
        UUID brandId = UUID.randomUUID();
        productService.findAllByBrandId(brandId, pageable);
        verify(productRepository).findAllByBrandId(brandId, pageable);
    }

    @Test
    void findAllEnabledProducts() {
        productService.findAllEnabledProducts(pageable);
        verify(productRepository).findAllEnabledProducts(pageable);
    }

    @Test
    void canFindById() {
        UUID id = UUID.randomUUID();
        given(productRepository.findById(id))
                .willReturn(java.util.Optional.of(product));
        Product product = productService.findById(id);
        assertThat(product).isEqualTo(this.product);
    }

    @Test
    void canNotFindById() {
        UUID id = UUID.randomUUID();
        given(productRepository.findById(id))
                .willReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> productService.findById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found");
    }

    @Test
    void canSave() {
        product = new Product("Test", "Test", "Test",
                0, 0, 0, 0, false);
        UUID brandId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        given(cloudinaryService.upload(multipartFile)).willReturn("Test");
        productService.save
                ("Test", multipartFile, "Test",
                        0, 0, 0, 0, categoryId, brandId);
        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(product);
    }

    @Test
    void existsProductHasThisNameWhenSave() {
        UUID brandId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        given(productRepository.findByName("Test")).willReturn(product);
        assertThatThrownBy(() -> productService.save
                ("Test", multipartFile, "Test",
                        0, 0, 0, 0, categoryId, brandId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("name already exists");
    }

    @Test
    void canUpdate() {
        UUID id = UUID.randomUUID();
        product = new Product("Test", "Test", "Test",
                0, 0, 0, 0, false);
        product.setId(id);
        UUID brandId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        given(cloudinaryService.upload(multipartFile)).willReturn("Test");
        given(productRepository.findById(id)).willReturn(java.util.Optional.of(product));
        productService.update(id, "Test", multipartFile, "Test", "Test",
                0, 0, 0, 0, categoryId, brandId);
        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(argumentCaptor.capture());
        System.out.println(argumentCaptor.getValue());
        assertThat(argumentCaptor.getValue()).isEqualTo(product);
    }

    @Test
    void existProductHaveThisNameWhenUpdate() {
        UUID id = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        given(productRepository.findByNameExceptId(id, "Test")).willReturn(product);
        assertThatThrownBy(() -> productService.update
                (id, "Test", multipartFile, "Test", "Test",
                        0, 0, 0, 0, categoryId, brandId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("product already exists");
    }

    @Test
    void notExistProductUpdate() {
        UUID id = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        given(productRepository.findById(id)).willReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> productService.update
                (id, "Test", multipartFile, "Test", "Test",
                        0, 0, 0, 0, categoryId, brandId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found");
    }


    @Test
    void notExistProductForUpdateQuantityAfterOrder() {
        UUID id = UUID.randomUUID();
        UUID brandId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        given(productRepository.findById(id)).willReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> productService.update
                (id, "Test", multipartFile, "Test", "Test",
                        0, 0, 0, 0, categoryId, brandId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found");
    }

    @Test
    void updateProductQuantityAfterOrder() {
        UUID id = UUID.randomUUID();
        given(productRepository.findById(id)).willReturn(java.util.Optional.of(product));
        productService.updateProductQuantityAfterOrder(id, 1);
        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(product);
    }

    @Test
    void canDelete() {
        UUID id = UUID.randomUUID();
        given(productRepository.findById(id)).willReturn(java.util.Optional.of(product));
        productService.delete(id);
        verify(productRepository).save(product);
    }

    @Test
    void canNotDelete() {
        UUID id = UUID.randomUUID();
        given(productRepository.findById(id)).willReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> productService.delete(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found");
    }

}