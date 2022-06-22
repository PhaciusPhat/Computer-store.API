package com.example.demo.services;

import com.example.demo.response.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductDTOService {
    List<ProductDTO> findAllDTO();
    List<ProductDTO> findAllDTOByCategoryId(UUID id);
    List<ProductDTO> findAllDTOByBrandId(UUID id);
    ProductDTO findByIdDTO(UUID id);
}
