package com.example.demo.services;

import com.example.demo.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> findAll();
    List<Product> findAllByCategoryId(UUID id);
    List<Product> findAllByBrandId(UUID id);
    Product findById(UUID id);
    Product create(String name, MultipartFile file, String description, int priceIn,
                   int priceOut, int quantity, int discount, UUID categoryId, UUID brandId);
    Product update(UUID id, String name, MultipartFile file, String urlMainImage, String description, int priceIn,
                   int priceOut, int quantity, int discount, UUID categoryId, UUID brandId);
    void delete(UUID id);
}
