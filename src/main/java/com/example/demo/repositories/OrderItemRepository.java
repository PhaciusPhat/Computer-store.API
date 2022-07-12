package com.example.demo.repositories;

import com.example.demo.models.ManyToManyPrimaryKey.OrderItemKey;
import com.example.demo.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {
    @Query("SELECT o FROM OrderItem o WHERE o.orderItemKey.orderId = :orderId")
    List<OrderItem> getCartItems(UUID orderId);
}
