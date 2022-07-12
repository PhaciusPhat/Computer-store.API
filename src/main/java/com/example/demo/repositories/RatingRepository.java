package com.example.demo.repositories;

import com.example.demo.models.ManyToManyPrimaryKey.RatingKey;
import com.example.demo.models.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
    @Query("SELECT r FROM Rating r WHERE r.ratingKey.productId = :productId")
    Page<Rating> getAllRatingsByProduct(UUID productId, Pageable pageable);

    @Query("SELECT r FROM Rating r WHERE r.ratingKey.accountId = :accountId AND r.ratingKey.productId = :productId")
    Rating getRatingByAccountAndProduct(UUID accountId, UUID productId);
}
