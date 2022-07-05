package com.example.demo.services.Impl;

import com.example.demo.models.Account;
import com.example.demo.models.ManyToManyPrimaryKey.RatingKey;
import com.example.demo.models.Product;
import com.example.demo.models.Rating;
import com.example.demo.repositories.RatingRepository;
import com.example.demo.request.RatingRequest;
import com.example.demo.response.dto.AccountDTO;
import com.example.demo.response.dto.RatingDTO;
import com.example.demo.services.AccountService;
import com.example.demo.services.ProductService;
import com.example.demo.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final AccountService accountService;
    private final ProductService productService;

    private final ModelMapper modelMapper;

    public RatingServiceImpl(RatingRepository ratingRepository, AccountService accountService, ProductService productService, ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.accountService = accountService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    private RatingDTO convertToDTO(Rating rating) {
        RatingDTO ratingDTO = modelMapper.map(rating, RatingDTO.class);
        ratingDTO.setAccountName(rating.getAccount().getName());
        return ratingDTO;
    }

    @Override
    public Page<RatingDTO> getAllRatings(UUID productId, Pageable pageable) {
        return ratingRepository.getAllRatingsByProduct(productId, pageable).map(this::convertToDTO);
    }

    @Override
    public Rating save(RatingRequest ratingRequest) {
        Account account = accountService.findLocalAccountByUsername();
        Product product = productService.findById(ratingRequest.getProductId());
        Rating checkRating = ratingRepository.getRatingByAccountAndProduct
                (account.getId(), ratingRequest.getProductId());
        if (checkRating == null) {
            checkRating = new Rating();
            checkRating.setRatingKey(new RatingKey
                    (account.getId(), ratingRequest.getProductId()));
        }
        checkRating.setStars(ratingRequest.getStars());
        checkRating.setComment(ratingRequest.getComment());
        checkRating.setAccount(account);
        checkRating.setProduct(product);
        return ratingRepository.save(checkRating);
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

