package com.example.demo.controllers;

import com.example.demo.services.CloudinaryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/general")
public class GeneralController {
    private final CloudinaryService cloudinaryService;

    public GeneralController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public String getGeneral() {
        return "General";
    }

    @PostMapping
    public String postGeneral(@RequestParam("file")MultipartFile file) {
        return cloudinaryService.upload(file);
    }

    @PutMapping
    public String putGeneral(@RequestBody String x) {
        return x;
    }

    @DeleteMapping
    public String deleteGeneral() {
        return "General";
    }

}
