package com.example.demo.controllers.user;

import com.example.demo.services.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/brand")
public class BrandClientController {
    private final BrandService brandService;

    public BrandClientController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(brandService.findAllEnabledBrands());
    }
}
