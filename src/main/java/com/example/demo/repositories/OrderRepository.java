package com.example.demo.repositories;

import com.example.demo.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM OrderEntity o WHERE o.account.id = :accountId")
    Page<Order> findAllByAccountId(UUID accountId, Pageable pageable);
}
