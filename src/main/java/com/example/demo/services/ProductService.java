package com.example.demo.services;

import com.example.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

//import java.util.List;
import java.util.UUID;
//    Product updateQuantityAndImage(UUID id, int quantity, MultipartFile file);
public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByCategoryId(UUID id, Pageable pageable);
    Page<Product> findAllByBrandId(UUID id, Pageable pageable);
    Page<Product> findAllEnabledProducts(Pageable pageable);
    Product findById(UUID id);
    Product save(String name, MultipartFile file, String description, int priceIn,
                   int priceOut, int quantity, int discount, UUID categoryId, UUID brandId);
    Product update(UUID id, String name, MultipartFile file, String urlMainImage, String description, int priceIn,
                   int priceOut, int quantity, int discount, UUID categoryId, UUID brandId);
    void updateProductQuantityAfterOrder(UUID id, int minusQuantity);
    void delete(UUID id);
}
