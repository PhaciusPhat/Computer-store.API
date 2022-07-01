package com.example.demo.services;

import com.example.demo.response.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductDTOService {
    Page<ProductDTO> findAllDTO(Pageable pageable);
    List<ProductDTO> findAllByRating();
    Page<ProductDTO> findAllDTOByCategoryId(UUID id, Pageable pageable);
    Page<ProductDTO> findAllDTOByBrandId(UUID id, Pageable pageable);
    ProductDTO findByIdDTO(UUID id);
}
