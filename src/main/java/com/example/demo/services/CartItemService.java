package com.example.demo.services;

import com.example.demo.models.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
    List<CartItem> getCartItems();
    CartItem save(UUID productId, int quantity);
    void delete(List<UUID> productIds);
}
