package com.example.demo.controllers.admin;
import com.example.demo.models.Product;
import com.example.demo.services.ProductImagesService;
import com.example.demo.services.ProductService;
import com.example.demo.utils.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/product")
public class ProductController {
    private final ProductService productService;
    private final Utilities utilities;

    private final ProductImagesService productImagesService;

    public ProductController(ProductService productService, Utilities utilities, ProductImagesService productImagesService) {
        this.productService = productService;
        this.utilities = utilities;
        this.productImagesService = productImagesService;
    }


    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        return ResponseEntity.ok(productService.findAll(utilities.createPageable(page, size, sort)));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findAllByCategoryId(@PathVariable UUID id,
                                                 @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                 @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        return ResponseEntity.ok(productService.findAllByCategoryId(id, utilities.createPageable(page, size, sort)));
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<?> findAllByBrandId(@PathVariable UUID id,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                              @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return ResponseEntity.ok(productService.findAllByBrandId(id, utilities.createPageable(page, size, sort)));
    }
    
    @PostMapping
    public ResponseEntity<?> save(@RequestParam String name, @RequestParam MultipartFile file,
                                  @RequestParam String description, @RequestParam int priceIn,
                                  @RequestParam int priceOut, @RequestParam int quantity,
                                  @RequestParam int discount, @RequestParam UUID categoryId,
                                  @RequestParam UUID brandId) {
        Product p =  productService.save(name, file, description, priceIn, priceOut, quantity, discount, categoryId, brandId);
        productImagesService.save(p.getUrlMainImage(), p);
        return ResponseEntity.ok("Product saved");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestParam String name, @RequestParam(value = "file", required = false) MultipartFile file,
                                    @RequestParam String urlMainImage, @RequestParam String description, @RequestParam int priceIn,
                                    @RequestParam int priceOut, @RequestParam int quantity, @RequestParam int discount,
                                    @RequestParam UUID categoryId, @RequestParam UUID brandId) {
        Product p = productService.update(id, name, file, urlMainImage, description, priceIn, priceOut, quantity, discount, categoryId, brandId);
        productImagesService.save(p.getUrlMainImage(), p);
        return ResponseEntity.ok("Product updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
