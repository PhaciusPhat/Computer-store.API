package com.example.demo.services.Impl;

import com.example.demo.models.Product;
import com.example.demo.models.ProductImages;
import com.example.demo.repositories.ProductImagesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static org.mockito.BDDMockito.given;
@DataJpaTest
class ProductImagesServiceImplTest {

    @Mock
    private ProductImagesRepository productImagesRepository;
    private ProductImagesServiceImpl productImagesService;

    @Mock
    private ProductImages productImages;

    @BeforeEach
    void setUp(){
        productImagesService = new ProductImagesServiceImpl(productImagesRepository);
    }


    @Test
    void canSave() {
        Product product = new Product();
        ProductImages productImages = new ProductImages("url");
        productImagesService.save("url", product);
        ArgumentCaptor<ProductImages> argumentCaptor = ArgumentCaptor.forClass(ProductImages.class);
        verify(productImagesRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getUrlImage()).isEqualTo("url");
    }

    @Test
    void canNotSave(){
        ProductImages productImages = new ProductImages("url");
        given(productImagesRepository.findByUrlImage("url")).willReturn(productImages);
        verify(productImagesRepository, never()).save(productImages);

    }

}