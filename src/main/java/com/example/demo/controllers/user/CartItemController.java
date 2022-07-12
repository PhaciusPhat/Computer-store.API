package com.example.demo.controllers.user;

import com.example.demo.request.OrderProductWithNumber;
import com.example.demo.services.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/cart")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(cartItemService.getCartItems());
    }

    @PostMapping
    public ResponseEntity<?> addToCart
            (@RequestBody OrderProductWithNumber orderProductWithNumber) {
        return ResponseEntity.ok(cartItemService.save
                (orderProductWithNumber.getProductId(),
                orderProductWithNumber.getNumber()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCartItem(@RequestBody List<UUID> productIds) {
        cartItemService.delete(productIds);
        return ResponseEntity.ok("CartItem deleted");
    }

}
