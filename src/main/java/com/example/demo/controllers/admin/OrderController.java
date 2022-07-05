package com.example.demo.controllers.admin;

import com.example.demo.enums.OrderStatus;
import com.example.demo.services.OrderItemService;
import com.example.demo.services.OrderService;
import com.example.demo.utils.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final Utilities utilities;

    public OrderController(OrderService orderService, OrderItemService orderItemService, Utilities utilities) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.utilities = utilities;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return ResponseEntity.ok(orderService.findAll(utilities.createPageable(page, size, sort)));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderItemService.getAllOrderItemsByOrderId(id));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<?> findByAccountId(@PathVariable UUID id,
                                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                             @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                             @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return ResponseEntity.ok(orderService.findAllByAccountId(id, utilities.createPageable(page, size, sort)));
    }

    @PutMapping("/status/approve/{id}")
    public ResponseEntity<?> approveOrder(@PathVariable UUID id) {
        System.out.println("approveOrder");
        orderService.updateStatus(id, OrderStatus.APPROVED);
        return ResponseEntity.ok("Order approved");
    }

    @PutMapping("/status/reject/{id}")
    public ResponseEntity<?> rejectOrder(@PathVariable UUID id) {
        orderService.updateStatus(id, OrderStatus.REJECTED);
        return ResponseEntity.ok("Order rejected");
    }
}
