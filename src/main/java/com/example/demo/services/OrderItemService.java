package com.example.demo.services;

import com.example.demo.models.OrderItem;
import com.example.demo.response.dto.OrderItemDTO;

import java.util.List;
import java.util.UUID;

public interface OrderItemService {
    List<OrderItemDTO> getAllOrderItemsByOrderId(UUID orderId);
    void addOrderItem(OrderItem orderItem);
}
