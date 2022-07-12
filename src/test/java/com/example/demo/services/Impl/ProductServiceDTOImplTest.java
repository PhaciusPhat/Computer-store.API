package com.example.demo.services.Impl;

import com.example.demo.models.Product;
import com.example.demo.models.Rating;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.response.dto.ProductDTO;
import com.example.demo.response.dto.RatingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@DataJpaTest
class ProductServiceDTOImplTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Product product;
    private ProductServiceDTOImpl productService;

    @Mock
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceDTOImpl(modelMapper, productRepository);
    }

    @Test
    void findAllByRating() {
        productService.findAllByRating();
        verify(productRepository).findAllByRating();
    }
}