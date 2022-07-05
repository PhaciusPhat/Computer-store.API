package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.response.dto.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountService {
    Page<AccountDTO> findAllDTO(Pageable pageable);

    AccountDTO findDTOById(UUID id);

    AccountDTO findDTOByUsername();
    Account findLocalAccountByUsername();

    Account save(Account account);

    void updateAccountInformation
            (Account account);

    void updateAccountPassword
            (String username, String password,
             String newPassword);

    String requestActiveAccount();

    void activeAccount(String code,
                       String encryptString);

    void activeAccount(UUID id);

    void delete(UUID id);
}
