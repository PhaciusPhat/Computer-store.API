package com.example.demo.repositories;

import com.example.demo.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    @Query("SELECT b FROM Brand b WHERE lower(b.name) = lower(?1)")
    Brand findByName(String name);

    @Query("SELECT b FROM Brand b WHERE b.id <> ?1 AND lower(b.name) = lower(?2)")
    Brand findByNameExceptId(UUID id, String name);
}
