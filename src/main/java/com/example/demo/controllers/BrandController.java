package com.example.demo.controllers;
import com.example.demo.services.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
@RequestMapping("/api/public/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(brandService.findAll());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id){
        return ResponseEntity.ok(brandService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestParam("name") String name, @RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(brandService.save(name, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestParam("name") String name, @RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(brandService.update(id, name, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        brandService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
