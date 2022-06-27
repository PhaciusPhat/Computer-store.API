package com.example.demo.controllers;

import com.example.demo.services.ProductImagesService;
import com.example.demo.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/product")
public class ProductController {
    private final ProductService productService;
    private final ProductImagesService productImagesService;

    public ProductController(ProductService productService, ProductImagesService productImagesService) {
        this.productService = productService;
        this.productImagesService = productImagesService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findAllByCategoryId(@PathVariable UUID id){
        return ResponseEntity.ok(productService.findAllByCategoryId(id));
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<?> findAllByBrandId(@PathVariable UUID id){
        return ResponseEntity.ok(productService.findAllByBrandId(id));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<?> findAllByProductId(@PathVariable UUID id){
        return ResponseEntity.ok(productImagesService.findAllByProductId(id));
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestParam String name, @RequestParam MultipartFile file,
                                  @RequestParam String description, @RequestParam int priceIn,
                                  @RequestParam int priceOut, @RequestParam int quantity,
                                  @RequestParam int discount, @RequestParam UUID categoryId,
                                  @RequestParam UUID brandId){
        return ResponseEntity.ok(productService.create(name, file, description, priceIn, priceOut, quantity, discount, categoryId, brandId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestParam String name, @RequestParam MultipartFile file,
                                    @RequestParam String urlMainImage, @RequestParam String description, @RequestParam int priceIn,
                                    @RequestParam int priceOut, @RequestParam int quantity, @RequestParam int discount,
                                    @RequestParam UUID categoryId, @RequestParam UUID brandId){
        return ResponseEntity.ok(productService.update(id, name, file, urlMainImage, description, priceIn, priceOut, quantity, discount, categoryId, brandId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        productService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
