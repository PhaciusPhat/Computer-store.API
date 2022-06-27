package com.example.demo.repositories;

import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE p.isDisabled = ?1")
    List<Product> findAllByIsDisabled(boolean isDisabled);

    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    List<Product> findAllByCategoryId(UUID id);

    @Query("SELECT p FROM Product p WHERE p.brand.id = :id")
    List<Product> findAllByBrandId(UUID id);

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Product findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.id <> :id AND p.name = :name")
    Product findByNameExceptId(UUID id, String name);
}
