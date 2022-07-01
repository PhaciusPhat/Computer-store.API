package com.example.demo.repositories;

import com.example.demo.models.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import java.util.List;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE p.isDisabled = ?1")
    Page<Product> findAllEnabledProducts(boolean isDisabled, @NotNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :id")
    Page<Product> findAllByCategoryId(UUID id, @NotNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.brand.id = :id")
    Page<Product> findAllByBrandId(UUID id, @NotNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE lower(p.name) = lower(:name)")
    Product findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.id <> :id AND lower(p.name) = lower(:name)")
    Product findByNameExceptId(UUID id, String name);

    @Query("SELECT p FROM Product p " +
            "INNER JOIN Rating r " +
            "ON p.id = r.product.id " +
            "WHERE r.stars = " +
            "(select max(stars) from Rating) " +
            "AND p.isDisabled = false")
    List<Product> findAllByRating();

    @Query("SELECT p FROM Product p WHERE p.isDisabled = false")
    Page<Product> findAllEnabledProducts(@NotNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isDisabled = false AND p.category.id = :id")
    Product findByIdEnabledProduct(UUID id);

    @Query("SELECT p FROM Product p WHERE p.category.id = :id AND p.isDisabled = false")
    Page<Product> findAllEnabledProductsByCategoryId(UUID id, @NotNull Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.brand.id = :id AND p.isDisabled = false")
    Page<Product> findAllEnabledProductsByBrandId(UUID id, @NotNull Pageable pageable);
}
