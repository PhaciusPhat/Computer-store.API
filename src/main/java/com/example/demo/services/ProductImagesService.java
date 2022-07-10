package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.models.ProductImages;

import java.util.List;
import java.util.UUID;

public interface ProductImagesService {
    ProductImages save(String url, Product product);
}
