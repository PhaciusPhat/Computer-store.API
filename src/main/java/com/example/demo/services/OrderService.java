package com.example.demo.services;

import com.example.demo.enums.OrderStatus;
import com.example.demo.models.Order;
import com.example.demo.request.OrderRequest;
import com.example.demo.response.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    Page<OrderDTO> findAll(Pageable pageable);
    Page<OrderDTO> findAllByAccountId(UUID accountId, Pageable pageable);
    Page<OrderDTO> findAllByLocalAccount(Pageable pageable);
    Order save (OrderRequest orderRequest);
    void updateStatus(UUID orderId, OrderStatus status);
}
