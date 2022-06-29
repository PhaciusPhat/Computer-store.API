package com.example.demo.services;

import com.example.demo.enums.OrderStatus;
import com.example.demo.models.Order;
import com.example.demo.response.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    Page<OrderDTO> findAll(Pageable pageable);
    Page<OrderDTO> findAllByAccountId(UUID accountId, Pageable pageable);
    Order save (UUID accountId, long total, String address, String description);
    void updateStatus(UUID orderId, OrderStatus status);
}
