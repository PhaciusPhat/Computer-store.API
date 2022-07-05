package com.example.demo.repositories;

import com.example.demo.models.CartItem;
import com.example.demo.models.ManyToManyPrimaryKey.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {

    @Query("SELECT c FROM CartItem c WHERE c.cartItemKey.accountId = :accountId")
    List<CartItem> getCartItems(UUID accountId);

    @Query("SELECT c FROM CartItem c WHERE c.cartItemKey.accountId = :accountId AND c.cartItemKey.productId = :productId")
    CartItem getCartItemByAccountIdAndProductId(UUID accountId, UUID productId);


}
