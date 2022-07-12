package com.example.demo.response.dto;

import com.example.demo.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    private UUID accountId;
    private String AccountUsername;
    private String AccountName;
    private long total;
    private String address;
    private String description;
    private long createdDate;

}
