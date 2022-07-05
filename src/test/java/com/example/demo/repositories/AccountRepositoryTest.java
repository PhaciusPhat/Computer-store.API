package com.example.demo.repositories;

import com.example.demo.enums.Role;
import com.example.demo.models.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Disabled
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void CheckNotExistsUser() {
        String username = "test";
        Account account = accountRepository.findByUsername(username);
        assert account == null;
    }


    @Test
    void CheckExistsUser() {
        Account account = new Account(
                "test55",
                "test55",
                "test55@gmail.com",
                "test55",
                "1234567890",
                Role.USER,
                true,
                false
        );
        accountRepository.save(account);
        Account account1 = accountRepository.findByUsername("test55");
        assert account1 != null;
    }

    @Test
    void CheckNotExistsUserHaveEmail() {
        String email = "test@gmail.com";
        Account account = accountRepository.findByEmail(email);
        assert account == null;
    }

    @Test
    void CheckExistsUserHaveEmail() {
        String email = "test55@gmail.com";
        Account account = new Account(
                "test55",
                "test55",
                "test55@gmail.com",
                "test55",
                "1234567890",
                Role.USER,
                true,
                false
        );
        accountRepository.save(account);
        Account account1 = accountRepository.findByEmail(email);
        assert account1 != null;
    }
}