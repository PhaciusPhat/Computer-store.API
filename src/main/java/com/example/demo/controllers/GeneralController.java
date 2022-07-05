package com.example.demo.controllers;

import com.example.demo.services.CloudinaryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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
    public String putGeneral(@RequestBody List<UUID> x) {
        return x.get(0).toString();
    }

    @DeleteMapping
    public String deleteGeneral() {
        return "General";
    }

}
