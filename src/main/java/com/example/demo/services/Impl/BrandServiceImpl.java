package com.example.demo.services.Impl;

import com.example.demo.models.Brand;
import com.example.demo.repositories.BrandRepository;
import com.example.demo.services.BrandService;
import com.example.demo.services.CloudinaryService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CloudinaryService cloudinaryService;

    public BrandServiceImpl(BrandRepository brandRepository, CloudinaryService cloudinaryService) {
        this.brandRepository = brandRepository;
        this.cloudinaryService = cloudinaryService;
    }

    private boolean findByName(String name) {
        return brandRepository.findByName(name) != null;
    }

    private boolean findByNameExceptId(UUID id, String name) {
        return brandRepository.findByNameExceptId(id, name) != null;
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    @SneakyThrows
    public Brand findById(UUID id) {
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
    }

    @Override
    public Brand save(String name, MultipartFile file) {
        if (findByName(name)) {
            throw new IllegalArgumentException("Brand already exists");
        }
        Brand brand = new Brand(name, cloudinaryService.upload(file));
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(UUID id, String name, MultipartFile file) {
        if (findByNameExceptId(id, name)) {
            throw new IllegalArgumentException("Brand already exists");
        }
        Brand brand = findById(id);
        brand.setName(name);
        brand.setUrlImage(file.getSize() != 0 ? cloudinaryService.upload(file) : brand.getUrlImage());
        return brandRepository.save(brand);
    }


    @Override
    public void delete(UUID id) {
        Brand brand = findById(id);
        brandRepository.delete(brand);
    }
}
