package com.example.demo.controllers.user;

import com.example.demo.models.Rating;
import com.example.demo.request.RatingRequest;
import com.example.demo.services.RatingService;
import com.example.demo.utils.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/rating")
public class RatingController {
    private final RatingService ratingService;
    private final Utilities utilities;

    public RatingController(RatingService ratingService, Utilities utilities) {
        this.ratingService = ratingService;
        this.utilities = utilities;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRating(@PathVariable UUID id,
       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
       @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return ResponseEntity.ok(ratingService.getAllRatings(id, utilities.createPageable(page, size, sort)));
    }

    @PostMapping
    public ResponseEntity<?> saveRating(@RequestBody RatingRequest ratingRequest) {
        return ResponseEntity.ok(ratingService.save(ratingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable UUID id) {
        ratingService.delete(id);
        return ResponseEntity.ok("Rating deleted");
    }
}
