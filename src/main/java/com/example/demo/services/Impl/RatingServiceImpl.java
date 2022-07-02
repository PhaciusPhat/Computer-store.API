package com.example.demo.services.Impl;

import com.example.demo.models.ManyToManyPrimaryKey.RatingKey;
import com.example.demo.models.Rating;
import com.example.demo.repositories.RatingRepository;
import com.example.demo.response.dto.AccountDTO;
import com.example.demo.services.AccountService;
import com.example.demo.services.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final AccountService accountService;

    public RatingServiceImpl(RatingRepository ratingRepository, AccountService accountService) {
        this.ratingRepository = ratingRepository;
        this.accountService = accountService;
    }




    @Override
    public Page<Rating> getAllRatings(UUID productId, Pageable pageable) {
        return ratingRepository.getAllRatingsByProduct(productId, pageable);
    }

    @Override
    public Rating save(UUID productId, int stars, String comment) {
        AccountDTO account = accountService.findDTOByUsername();
        Rating rating = ratingRepository.getRatingByAccountAndProduct(account.getId(), productId);
        if (rating == null) {
            rating.setRatingKey(new RatingKey(account.getId(), productId));
        }
        rating.setStars(stars);
        rating.setComment(comment);
        return ratingRepository.save(rating);
    }

    @Override
    public void delete(UUID productId) {
        AccountDTO account = accountService.findDTOByUsername();
        Rating rating = ratingRepository.getRatingByAccountAndProduct(account.getId(), productId);
        if (rating != null) {
            ratingRepository.delete(rating);
        } else {
            throw new NotFoundException("Rating not found");
        }
    }
}

