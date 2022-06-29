package com.example.demo.services;

import com.example.demo.models.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RatingService {
    Page<Rating> getAllRatings(UUID productId,
                               Pageable pageable);
    Rating save(Rating rating);
    void update(Rating rating);
    void delete(UUID accountId, UUID productId);
}
