package com.example.demo.services.Impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Brand;
import com.example.demo.models.Category;
import com.example.demo.repositories.BrandRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.response.dto.BrandDTO;
import com.example.demo.response.dto.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CloudinaryServiceImpl cloudinaryService;
    private BrandServiceImpl brandService;
    @Mock
    private Brand brand;
    @Mock
    private BrandDTO brandDTO;
    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        brandService =
                new BrandServiceImpl(brandRepository, cloudinaryService, modelMapper);
    }

    @Test
    void convertToDTO() {
        brandService.convertToDTO(brand);
        verify(modelMapper).map(brand, BrandDTO.class);
    }

    @Test
    void findAll() {
        brandService.findAll();
        verify(brandRepository).findAll();
    }

    @Test
    void findAllEnabledBrands() {
        brandService.findAllEnabledBrands();
        verify(brandRepository).findAllEnabledBrands();
    }

    @Test
    void canFindById() {
        UUID id = UUID.randomUUID();
        when(brandRepository.findById(id)).thenReturn(java.util.Optional.of(brand));
        Brand brand = brandService.findById(id);
        assertThat(brand).isEqualTo(this.brand);
    }

    @Test
    void canNotFindById() {
        UUID id = UUID.randomUUID();
        when(brandRepository.findById(id)).thenReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> brandService.findById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found");
    }

    @Test
    void canSave() {
        Brand brand = new Brand("test", "test", false);
        given(cloudinaryService.upload(multipartFile)).willReturn("test");
        brandService.save("test", multipartFile);
        ArgumentCaptor<Brand> argumentCaptor = ArgumentCaptor.forClass(Brand.class);
        verify(brandRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(brand);
    }

    @Test
    void existBrandHaveThisNameWhenAdd () {
        given(brandRepository.findByName("test")).willReturn(brand);
        assertThatThrownBy(() -> brandService.save("test", multipartFile))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Brand already exists");
    }

    @Test
    void canUpdate() {
        UUID id = UUID.randomUUID();
//        Brand brand = new Brand("test", "test", false);
        given(brandRepository.findById(id)).willReturn(java.util.Optional.of(brand));
        given(cloudinaryService.upload(multipartFile)).willReturn("test");
        brandService.update(id, "test", multipartFile);
        ArgumentCaptor<Brand> argumentCaptor = ArgumentCaptor.forClass(Brand.class);
        verify(brandRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(brand);

    }

    @Test
    void existBrandHaveThisNameWhenUpdate() {
        UUID id = UUID.randomUUID();
        Brand brand = new Brand("test", "test", false);
        given(brandRepository.findByNameExceptId(id, "test"))
                .willReturn(brand);
        assertThatThrownBy(() -> brandService.update(id, "test", multipartFile))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Brand already exists");
    }

    @Test
    void notExistBrandUpdate() {
        UUID id = UUID.randomUUID();
        Brand brand = new Brand("test", "test", false);
        given(brandRepository.findById(id)).willReturn(java.util.Optional.empty());
        assertThatThrownBy(() -> brandService.update(id, "test", multipartFile))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found");
    }

    @Test
    void delete() {
        UUID id = UUID.randomUUID();
        given(brandRepository.findById(id)).willReturn(java.util.Optional.of(brand));
        brandService.delete(id);
        verify(brandRepository).save(brand);
    }

    @Test
    void canNotDelete() {
        UUID id = UUID.randomUUID();
        assertThatThrownBy(() -> brandService.update(id, "test", multipartFile))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Not found");
    }
}