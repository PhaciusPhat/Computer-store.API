package com.example.demo.repositories;

import com.example.demo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("SELECT a FROM Account a WHERE a.username = ?1")
    Account findByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    Account findByEmail(String email);
}
