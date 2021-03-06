package com.example.demo.services.Impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Brand;
import com.example.demo.repositories.BrandRepository;
import com.example.demo.response.dto.BrandDTO;
import com.example.demo.services.BrandService;
import com.example.demo.services.CloudinaryService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CloudinaryService cloudinaryService;

    private final ModelMapper modelMapper;

    public BrandServiceImpl(BrandRepository brandRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    public BrandDTO convertToDTO(Brand brand) {
        return brand != null ? modelMapper.map(brand, BrandDTO.class) : null;
    }

    private boolean findByName(String name) {
        Brand x = brandRepository.findByName(name);
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
    public List<BrandDTO> findAllEnabledBrands() {
        return brandRepository.findAllEnabledBrands().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public Brand findById(UUID id) {
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
    }

    @Override
    public Brand save(String name, MultipartFile file) {
        if (findByName(name)) {
            throw new BadRequestException("Brand already exists");
        }
        Brand brand = new Brand(name, cloudinaryService.upload(file), false);
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(UUID id, String name, MultipartFile file) {
        if (findByNameExceptId(id, name)) {
            throw new BadRequestException("Brand already exists");
        }
        Brand brandToUpdate = findById(id);
        brandToUpdate.setName(name);
        brandToUpdate.setUrlImage(file != null ? cloudinaryService.upload(file) : brandToUpdate.getUrlImage());
        return brandRepository.save(brandToUpdate);
    }

    @Override
    public void delete(UUID id) {
        Brand brand = findById(id);
        brand.setDisabled(!brand.isDisabled());
        brandRepository.save(brand);
    }
}
