package com.example.demo.models.ManyToManyPrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemKey implements Serializable {
    @Column(name = "orderId")
    private UUID orderId;
    @Column(name = "productId")
    private UUID productId;
}
