package com.example.demo.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private int quantity;
    private UUID productId;
    private String productName;
    private String productUrlMainImage;
    private int productPriceOut;
    private int productQuantity;
    private int productDiscount;
    private boolean isDisabled;
}
