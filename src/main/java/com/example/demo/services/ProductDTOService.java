package com.example.demo.services;

import com.example.demo.response.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductDTOService {
    Page<ProductDTO> findAllDTO();
    Page<ProductDTO> findAllByRating();
    Page<ProductDTO> findAllDTOByCategoryId(UUID id);
    Page<ProductDTO> findAllDTOByBrandId(UUID id);
    ProductDTO findByIdDTO(UUID id);
}
