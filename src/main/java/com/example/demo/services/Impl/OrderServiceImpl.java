package com.example.demo.services.Impl;

import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Account;
import com.example.demo.models.ManyToManyPrimaryKey.OrderItemKey;
import com.example.demo.models.Order;
import com.example.demo.models.OrderItem;
import com.example.demo.models.Product;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.request.OrderProductWithNumber;
import com.example.demo.request.OrderRequest;
import com.example.demo.response.dto.OrderDTO;
import com.example.demo.services.*;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AccountService accountService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final OrderItemService orderItemService;

    public OrderServiceImpl(OrderRepository orderRepository, AccountService accountService, ProductService productService, CartItemService cartItemService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.accountService = accountService;
        this.productService = productService;
        this.cartItemService = cartItemService;
        this.orderItemService = orderItemService;
    }

    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(order.getId(),
                order.getAccount().getId(),
                order.getAccount().getUsername(),
                order.getAccount().getName(),
                order.getTotal(), order.getAddress(),
                order.getDescription(),
                order.getCreatedDate());
    }

    private Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Order not found"));
    }

    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public Page<OrderDTO> findAllByAccountId(UUID accountId, Pageable pageable) {
        return orderRepository.findAllByAccountId
                (accountService.findDTOById(accountId).getId(), pageable).map(this::convertToDTO);
    }

    @Override
    public Page<OrderDTO> findAllByLocalAccount(Pageable pageable) {
        return orderRepository.findAllByAccountId
                (accountService.findDTOByUsername().getId(), pageable).map(this::convertToDTO);
    }

    @Override
    public Order save(OrderRequest orderRequest) {
        Account account = accountService.findLocalAccountByUsername();
        if(!account.getIsActive()) {
            throw new BadRequestException("Account is not active");
        }
        long total = 0;
        List<UUID> productIds = new ArrayList<>();
        if (orderRequest.getOrderProducts().size() == 0) {
            throw new BadRequestException("Order must have at least one product");
        }

//      check number of products in order and calculate number of products in cart
        for (OrderProductWithNumber orderProductWithNumber : orderRequest.getOrderProducts()) {
            if (orderProductWithNumber.getNumber() < 1) {
                throw new BadRequestException("Invalid number of product");
            }
            if (orderProductWithNumber.getNumber() > productService.findById(orderProductWithNumber.getProductId()).getQuantity()) {
                throw new BadRequestException("Not enough product");
            }
            if(productService.findById(orderProductWithNumber.getProductId()).isDisabled()) {
                throw new BadRequestException("Product is disabled");
            }
            long productPrice = (long) (productService.findById(orderProductWithNumber.getProductId()).getPriceOut()
                                - productService.findById(orderProductWithNumber.getProductId()).getPriceOut() *
                                ((float) productService.findById(orderProductWithNumber.getProductId()).getDiscount() / 100.0));

            total += productPrice * orderProductWithNumber.getNumber();
            productIds.add(orderProductWithNumber.getProductId());
        }
//      delete products from cart
        cartItemService.delete(productIds);
//      save order
        Order order = new Order();
        order.setTotal(total);
        order.setAddress(orderRequest.getAddress());
        order.setDescription(orderRequest.getDescription());
        order.setAccount(account);
        Order saveOrder = orderRepository.save(order);
//      update product quantity
        for (OrderProductWithNumber orderProductWithNumber : orderRequest.getOrderProducts()) {

            productService.updateProductQuantityAfterOrder(orderProductWithNumber.getProductId(),
                    orderProductWithNumber.getNumber());
            OrderItemKey orderItemKey = new OrderItemKey(saveOrder.getId(), orderProductWithNumber.getProductId());
            OrderItem orderItem = new OrderItem();
            Product product = productService.findById(orderProductWithNumber.getProductId());
            orderItem.setOrderItemKey(orderItemKey);
            orderItem.setQuantity(orderProductWithNumber.getNumber());
            orderItem.setPrice((int) (product.getPriceOut() - product.getPriceOut() * ((float) product.getDiscount() / 100.0)));
            orderItem.setOrder(saveOrder);
            orderItem.setProduct(product);
            orderItemService.addOrderItem(orderItem);
        }
        return saveOrder;
    }

}
