package com.example.demo.services.Impl;

import com.example.demo.models.OrderItem;
import com.example.demo.repositories.OrderItemRepository;
import com.example.demo.response.dto.OrderItemDTO;
import com.example.demo.services.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        return orderItem != null ? modelMapper.map(orderItem, OrderItemDTO.class) : null;
    }

    @Override
    public List<OrderItemDTO> getAllOrderItemsByOrderId(UUID orderId) {
        return orderItemRepository.getCartItems(orderId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
