package com.example.demo.services.Impl;

import com.example.demo.models.Product;
import com.example.demo.models.ProductImages;
import com.example.demo.repositories.ProductImagesRepository;
import com.example.demo.services.ProductImagesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductImagesServiceImpl implements ProductImagesService {

    private final ProductImagesRepository productImagesRepository;


    ProductImagesServiceImpl(ProductImagesRepository productImagesRepository) {
        this.productImagesRepository = productImagesRepository;
    }

    private ProductImages findByUrlImage(String urlImage) {
        return productImagesRepository.findByUrlImage(urlImage);
    }

    @Override
    public ProductImages save(String url, Product product) {
        if (findByUrlImage(url) != null) {
            return null;
        }
        ProductImages productImages = new ProductImages(url);
        productImages.setProduct(product);
        return productImagesRepository.save(productImages);
    }

}

