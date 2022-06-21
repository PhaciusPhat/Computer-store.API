package com.example.demo.repositories;

import com.example.demo.models.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductImagesRepository extends JpaRepository<ProductImages, UUID> {
}
