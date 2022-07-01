package com.example.demo.controllers.user;

import com.example.demo.services.ProductDTOService;
import com.example.demo.utils.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/product")
public class ProductDTOController {
    private final ProductDTOService productDTOService;
    private final Utilities utilities;

    public ProductDTOController(ProductDTOService productDTOService, Utilities utilities) {
        this.productDTOService = productDTOService;
        this.utilities = utilities;
    }


    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        return ResponseEntity.ok(productDTOService.findAllDTO(utilities.createPageable(page, size, sort)));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(productDTOService.findByIdDTO(id));
    }

    @GetMapping("/rating")
    public ResponseEntity<?> findAllByRating() {
        return ResponseEntity.ok(productDTOService.findAllByRating());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findAllByCategoryId(@PathVariable UUID id,
                                                 @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
                                                 @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        return ResponseEntity.ok(productDTOService.findAllDTOByCategoryId(id, utilities.createPageable(page, size, sort)));
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<?> findAllByBrandId(@PathVariable UUID id,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
                                              @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return ResponseEntity.ok(productDTOService.findAllDTOByBrandId(id, utilities.createPageable(page, size, sort)));
    }
}

