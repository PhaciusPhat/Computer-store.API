package com.example.demo.services;

import com.example.demo.models.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderItemService {
    List<OrderItem> getAllOrderItemsByOrderId(UUID orderId);
    void addOrderItem(OrderItem orderItem);
}
