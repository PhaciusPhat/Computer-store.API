package com.example.demo.repositories;

import com.example.demo.models.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, UUID> {
    @Query("SELECT p FROM ProductImages p WHERE p.urlImage = ?1")
    ProductImages findByUrlImage(String urlImage);
    @Query("SELECT p FROM ProductImages p WHERE p.product.id = ?1")
    List<ProductImages> findAllByProductId(UUID id);
}
