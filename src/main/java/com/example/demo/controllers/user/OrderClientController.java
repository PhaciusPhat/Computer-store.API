package com.example.demo.controllers.user;

import com.example.demo.request.OrderRequest;
import com.example.demo.services.OrderItemService;
import com.example.demo.services.OrderService;
import com.example.demo.utils.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/order")
public class OrderClientController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final Utilities utilities;

    public OrderClientController(OrderService orderService, OrderItemService orderItemService, Utilities utilities) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.utilities = utilities;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort){
        return ResponseEntity.ok(orderService.findAllByLocalAccount(utilities.createPageable(page, size, sort)));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id){
        return ResponseEntity.ok(orderItemService.getAllOrderItemsByOrderId(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderService.save(orderRequest));
    }
}
