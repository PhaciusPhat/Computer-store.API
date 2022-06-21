package com.example.demo.services;

import com.example.demo.models.Brand;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<Brand> findAll();

    Brand findById(UUID id);

    Brand save(String name, MultipartFile file);

    Brand update(UUID id, String name, MultipartFile file);

    void delete(UUID id);
}
