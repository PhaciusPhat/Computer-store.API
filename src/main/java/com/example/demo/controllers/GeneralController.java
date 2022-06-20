package com.example.demo.controllers;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/general")
public class GeneralController {
    @GetMapping
    public String getGeneral() {
        return "General";
    }

    @PostMapping
    public String postGeneral() {
        return "General";
    }

    @PutMapping
    public String putGeneral() {
        return "General";
    }

    @DeleteMapping
    public String deleteGeneral() {
        return "General";
    }

}
