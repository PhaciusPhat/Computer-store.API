package com.example.demo.services.Impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Product;
import com.example.demo.models.ProductImages;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.*;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

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
        String url = file != null ? cloudinaryService.upload(file) : urlImage;
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
        return product;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllByCategoryId(UUID id, Pageable pageable) {
        return productRepository.findAllByCategoryId(id, pageable);
    }

    @Override
    public Page<Product> findAllByBrandId(UUID id, Pageable pageable) {
        return productRepository.findAllByBrandId(id, pageable);
    }

    @Override
    public Page<Product> findAllEnabledProducts(Pageable pageable) {
        return productRepository.findAllEnabledProducts(pageable);
    }

    @Override
    @SneakyThrows
    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
    }

    @Override
    public Product save(String name, MultipartFile file, String description,
                          int priceIn, int priceOut, int quantity, int discount, UUID categoryId, UUID brandId) {
        if (findByName(name) != null) {
            throw new BadRequestException("name already exists");
        } else {
            Product product = createProductWithImages(name, file, null, description,
                    priceIn, priceOut, quantity, discount, categoryId, brandId, null, false);
            return productRepository.save(product);
        }
    }

    @Override
    public Product update(UUID id, String name, MultipartFile file, String urlMainImage, String description, int priceIn,
                          int priceOut, int quantity, int discount, UUID categoryId, UUID brandId) {
        if (findByNameExceptId(id, name) != null) {
            throw new BadRequestException("product already exists");
        } else {
            Product product = findById(id);
            String url = urlMainImage.isEmpty() || urlMainImage.trim().equals("\"\"") ?
                    product.getUrlMainImage() : urlMainImage;
            product = createProductWithImages(name, file, url, description,
                    priceIn, priceOut, quantity, discount, categoryId, brandId, id, true);
            return productRepository.save(product);
        }
    }

    @Override
    public void updateProductQuantityAfterOrder(UUID id, int minusQuantity) {
        Product product = findById(id);
        product.setQuantity(product.getQuantity() - minusQuantity);
        productRepository.save(product);
    }

    @Override
    public void delete(UUID id) {
        Product product = findById(id);
        product.setDisabled(!product.isDisabled());
        productRepository.save(product);
    }
}
