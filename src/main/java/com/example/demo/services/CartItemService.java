package com.example.demo.services;

import com.example.demo.models.CartItem;
import com.example.demo.response.dto.CartItemDTO;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
    List<CartItemDTO> getCartItems();
    CartItem save(UUID productId, int quantity);
    void delete(List<UUID> productIds);
}
