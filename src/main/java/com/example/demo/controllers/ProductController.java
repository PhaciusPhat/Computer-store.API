package com.example.demo.controllers;

import com.example.demo.services.ProductImagesService;
import com.example.demo.services.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private Pageable createPageable(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        assert sortable != null;
        return PageRequest.of(page, size, sortable);
    }


    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        return ResponseEntity.ok(productService.findAll(createPageable(page, size, sort)));
//        return ResponseEntity.ok(productService.findAllEnabledProducts(createPageable(page, size, sort)));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findAllByCategoryId(@PathVariable UUID id,
         @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
         @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
         @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        return ResponseEntity.ok(productService.findAllByCategoryId(id, createPageable(page, size, sort)));
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<?> findAllByBrandId(@PathVariable UUID id,
      @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
      @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return ResponseEntity.ok(productService.findAllByBrandId(id, createPageable(page, size, sort)));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<?> findAllByProductId(@PathVariable UUID id) {
        return ResponseEntity.ok(productImagesService.findAllByProductId(id));
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestParam String name, @RequestParam MultipartFile file,
                                  @RequestParam String description, @RequestParam int priceIn,
                                  @RequestParam int priceOut, @RequestParam int quantity,
                                  @RequestParam int discount, @RequestParam UUID categoryId,
                                  @RequestParam UUID brandId) {
        return ResponseEntity.ok(productService.save(name, file, description, priceIn, priceOut, quantity, discount, categoryId, brandId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestParam String name, @RequestParam MultipartFile file,
                                    @RequestParam String urlMainImage, @RequestParam String description, @RequestParam int priceIn,
                                    @RequestParam int priceOut, @RequestParam int quantity, @RequestParam int discount,
                                    @RequestParam UUID categoryId, @RequestParam UUID brandId) {
        return ResponseEntity.ok(productService.update(id, name, file, urlMainImage, description, priceIn, priceOut, quantity, discount, categoryId, brandId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
