package com.example.demo.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    public String upload(MultipartFile multipartFile);
}
