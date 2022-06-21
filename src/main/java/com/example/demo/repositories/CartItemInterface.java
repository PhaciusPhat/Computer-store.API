package com.example.demo.repositories;

import com.example.demo.models.CartItem;
import com.example.demo.models.ManyToManyPrimaryKey.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemInterface extends JpaRepository<CartItem, CartItemKey> {
}
