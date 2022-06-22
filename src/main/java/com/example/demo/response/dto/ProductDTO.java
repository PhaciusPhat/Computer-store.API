package com.example.demo.response.dto;

import com.example.demo.models.ProductImages;
import com.example.demo.models.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private UUID id;
    private String name;
    private String urlMainImage;
    private String description;
    private int priceOut;
    private int discount;
    private int quantity;
    private int averageStars;
    private List<Rating> ratings;
    private List<ProductImages> productImages;
}
