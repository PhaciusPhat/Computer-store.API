package com.example.demo.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private int price;
    private int productDiscount;
    private String productName;
    private String productUrlMainImage;

}
