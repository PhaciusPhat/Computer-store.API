package com.example.demo.models;

import com.example.demo.models.ManyToManyPrimaryKey.OrderItemKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="OrderItem")
public class OrderItem {
    @JsonIgnore
    @EmbeddedId
    private OrderItemKey orderItemKey;
    private int price;
    private int quantity;

    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;


    @MapsId("productId")
    @JoinColumn(name = "productId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

}
