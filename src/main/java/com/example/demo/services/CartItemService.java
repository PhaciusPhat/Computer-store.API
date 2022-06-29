package com.example.demo.services;

import com.example.demo.models.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
    List<CartItem> getCartItems(UUID accountId);
    CartItem save(CartItem cartItem);
    void delete(UUID accountId, List<UUID> productIds);
}
