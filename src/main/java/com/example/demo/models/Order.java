package com.example.demo.models;

import com.example.demo.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "OrderEntity")
public class Order extends Auditable {
    @Id
    private UUID id;
    private long total;
    private String address;
    private String description;
    private OrderStatus status;

    public Order(long total, String address, String description, OrderStatus status) {
        this.total = total;
        this.address = address;
        this.description = description;
        this.status = status;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId")
    private Account account;
}
