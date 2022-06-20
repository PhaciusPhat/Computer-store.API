package com.example.demo.models;

import com.example.demo.models.ManyToManyPrimaryKey.CartItemKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cartItem")
public class CartItem {
    @EmbeddedId
    private CartItemKey cartItemKey;
    private int quantity;

    @JsonIgnore
    @MapsId("accountId")
    @JoinColumn(name="accountId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;


    @MapsId("productId")
    @JoinColumn(name="productId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
}
