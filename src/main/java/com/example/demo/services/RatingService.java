package com.example.demo.services;

import com.example.demo.models.Rating;
import com.example.demo.request.RatingRequest;
import com.example.demo.response.dto.RatingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RatingService {
    Page<RatingDTO> getAllRatings(UUID productId,
                                  Pageable pageable);
    Rating save(RatingRequest ratingRequest);
    void delete(UUID productId);
}
