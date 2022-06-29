package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.response.dto.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountService {
    Page<AccountDTO> findAll(Pageable pageable);
    AccountDTO findById(UUID id);
    AccountDTO findByUsername(String username);
    void save(Account account);
    void updateAccountInformation
            (String username, String name,
             String email, String phone);
    void updateAccountPassword
            (String username, String password,
             String newPassword);
    String requestActiveAccount(String username);
    void activeAccount(String username, String code,
                              String encodeString);
    void activeAccount(UUID id);
    void delete(UUID id);
}
