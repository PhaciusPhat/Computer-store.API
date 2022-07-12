package com.example.demo.services.Impl;

import com.example.demo.models.Account;
import com.example.demo.models.CartItem;
import com.example.demo.models.ManyToManyPrimaryKey.CartItemKey;
import com.example.demo.repositories.CartItemRepository;
import com.example.demo.response.dto.CartItemDTO;
import com.example.demo.services.AccountService;
import com.example.demo.services.CartItemService;
import com.example.demo.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final AccountService accountService;
    private final ProductService productService;

    private final ModelMapper modelMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, AccountService accountService, ProductService productService, ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.accountService = accountService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }

    @Override
    public List<CartItemDTO> getCartItems() {
        UUID accountId = accountService.findDTOByUsername().getId();
        return cartItemRepository.getCartItems(accountId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CartItem save(UUID productId, int quantity) {
        Account account = accountService.findLocalAccountByUsername();
        CartItem cartItem = cartItemRepository.getCartItemByAccountIdAndProductId(account.getId(), productId);
        if (cartItem == null) {
            cartItem = new CartItem();
            CartItemKey cartItemKey = new CartItemKey(account.getId(), productId);
            cartItem.setCartItemKey(cartItemKey);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setAccount(account);
        cartItem.setProduct(productService.findById(productId));
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
