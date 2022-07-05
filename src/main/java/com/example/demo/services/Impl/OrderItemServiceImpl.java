package com.example.demo.services.Impl;

import com.example.demo.models.OrderItem;
import com.example.demo.repositories.OrderItemRepository;
import com.example.demo.services.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> getAllOrderItemsByOrderId(UUID orderId) {
        return orderItemRepository.getCartItems(orderId);
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
