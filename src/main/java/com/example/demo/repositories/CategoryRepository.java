package com.example.demo.repositories;

import com.example.demo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("SELECT c FROM Category c WHERE lower(c.name) = lower(?1) ")
    Category findByName(String name);

    @Query("SELECT c FROM Category c WHERE lower(c.name) = lower(?2) AND c.id <> ?1")
    Category findByNameExceptId(UUID id, String name);

    @Query("SELECT b FROM Category b WHERE b.isDisabled = false")
    List<Category> findAllEnabledCategories();
}
