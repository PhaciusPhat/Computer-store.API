package com.example.demo.services.Impl;

import com.example.demo.models.CartItem;
import com.example.demo.models.ManyToManyPrimaryKey.CartItemKey;
import com.example.demo.repositories.CartItemRepository;
import com.example.demo.services.AccountService;
import com.example.demo.services.CartItemService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final AccountService accountService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, AccountService accountService) {
        this.cartItemRepository = cartItemRepository;
        this.accountService = accountService;
    }

    @Override
    public List<CartItem> getCartItems() {
        UUID accountId = accountService.findDTOByUsername().getId();
        return cartItemRepository.getCartItems(accountId);
    }

    @Override
    public CartItem save(UUID productId, int quantity) {
        UUID accountId = accountService.findDTOByUsername().getId();
        CartItem cartItem = cartItemRepository.getCartItemByAccountIdAndProductId(accountId, productId);
        if (cartItem == null) {
            CartItemKey cartItemKey = new CartItemKey(accountId, productId);
            cartItem.setCartItemKey(cartItemKey);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void delete(List<UUID> productIds) {
        UUID accountId = accountService.findDTOByUsername().getId();
        for (UUID productId : productIds) {
            CartItem cartItem = cartItemRepository.getCartItemByAccountIdAndProductId(accountId, productId);
            if (cartItem != null) {
                cartItemRepository.delete(cartItem);
            } else {
                throw new NotFoundException("CartItem not found");
            }
        }
    }
}
