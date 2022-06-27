package com.example.demo.services.Impl;

import com.example.demo.models.Product;
import com.example.demo.models.ProductImages;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProductImagesService productImagesService;

    public ProductServiceImpl(ProductRepository productRepository, CloudinaryService cloudinaryService,
                              BrandService brandService, CategoryService categoryService, ProductImagesService productImagesService) {
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productImagesService = productImagesService;
    }

    private Product findByName(String name) {
        return productRepository.findByName(name);
    }

    private Product findByNameExceptId(UUID id, String name) {
        return productRepository.findByNameExceptId(id, name);
    }

    private Product createProductWithImages(String name, MultipartFile file, String urlImage,
                                            String description, int priceIn, int priceOut, int quantity,
                                            int discount, UUID categoryId, UUID brandId, UUID id, boolean isUpdate) {
        String url = file.getSize() > 0 ? cloudinaryService.upload(file) : urlImage;
        Product product = new Product();
        if(isUpdate){
            product.setId(id);
        }
        product.setName(name);
        product.setUrlMainImage(url);
        product.setDescription(description);
        product.setPriceIn(priceIn);
        product.setPriceOut(priceOut);
        product.setQuantity(quantity);
        product.setDiscount(discount);
        product.setDisabled(false);
        product.setCategory(categoryService.findById(categoryId));
        product.setBrand(brandService.findById(brandId));
        ProductImages productImages = new ProductImages(url);
        productImages.setProduct(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllByCategoryId(UUID id) {
        return productRepository.findAllByCategoryId(id);
    }

    @Override
    public List<Product> findAllByBrandId(UUID id) {
        return productRepository.findAllByBrandId(id);
    }

    @Override
    @SneakyThrows
    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("not found"));
    }

    @Override
    public Product create(String name, MultipartFile file, String description,
                          int priceIn, int priceOut, int quantity, int discount, UUID categoryId, UUID brandId) {
        if (findByName(name) != null) {
            throw new IllegalArgumentException("name already exists");
        } else {
            Product product = productRepository.save(createProductWithImages(name, file, null, description,
                    priceIn, priceOut, quantity, discount, categoryId, brandId, null, false));
            productImagesService.save(product.getUrlMainImage(), product);
            return product;
        }
    }

    @Override
    public Product update(UUID id, String name, MultipartFile file, String urlMainImage, String description, int priceIn,
                          int priceOut, int quantity, int discount, UUID categoryId, UUID brandId) {
        if (findByNameExceptId(id, name) != null) {
            throw new IllegalArgumentException("product already exists");
        } else {
            Product product = findById(id);
            String url = urlMainImage.isEmpty() ? product.getUrlMainImage() : urlMainImage;
            product = productRepository.save(createProductWithImages(name, file, url, description,
                    priceIn, priceOut, quantity, discount, categoryId, brandId, id, true));
            productImagesService.save(product.getUrlMainImage(), product);
            return product;
        }
    }

    @Override
    public void delete(UUID id) {
        Product product = findById(id);
        product.setDisabled(!product.isDisabled());
        productRepository.save(product);
    }
}
