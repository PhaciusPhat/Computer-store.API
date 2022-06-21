package com.example.demo.repositories;

import com.example.demo.models.ManyToManyPrimaryKey.RatingKey;
import com.example.demo.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
}
