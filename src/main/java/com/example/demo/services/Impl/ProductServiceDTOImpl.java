package com.example.demo.services.Impl;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.response.dto.ProductDTO;
import com.example.demo.services.ProductDTOService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceDTOImpl implements ProductDTOService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public ProductServiceDTOImpl(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    public ProductDTO convertToDto(Product product) {
        if(product == null) {
            return null;
        }
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setProductImages(product.getProductImages());
        productDTO.setRatings(product.getRatings());
        return productDTO;
    }

    @Override
    public Page<ProductDTO> findAllDTO(Pageable pageable) {
        return productRepository.findAllEnabledProducts(pageable).map(this::convertToDto);
    }

    @Override
    public List<ProductDTO> findAllByRating() {
        return productRepository.findAllByRating()
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> findAllDTOByCategoryId(UUID id, Pageable pageable) {
        return productRepository.findAllEnabledProductsByCategoryId(id, pageable).map(this::convertToDto);
    }

    @Override
    public Page<ProductDTO> findAllDTOByBrandId(UUID id, Pageable pageable) {
        return productRepository.findAllEnabledProductsByBrandId(id, pageable).map(this::convertToDto);
    }

    @Override
    public ProductDTO findByIdDTO(UUID id) {
        System.out.println("id: " + id);
        return convertToDto(productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found")));
    }
}
