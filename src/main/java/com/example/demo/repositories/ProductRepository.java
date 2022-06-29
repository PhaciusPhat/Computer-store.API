package com.example.demo.repositories;

import com.example.demo.models.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE p.isDisabled = ?1")
    Page<Product> findAllEnabledProducts(boolean isDisabled, @NotNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    Page<Product> findAllByCategoryId(UUID id, @NotNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.brand.id = :id")
    Page<Product> findAllByBrandId(UUID id, @NotNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Product findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.id <> :id AND p.name = :name")
    Product findByNameExceptId(UUID id, String name);

    @Query("SELECT p FROM Product p")
    @NotNull
    Page<Product> findAll(@NotNull Pageable pageable);
}
