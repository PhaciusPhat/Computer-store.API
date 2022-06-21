package com.example.demo.services;

import com.example.demo.models.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface JwtUserDetailService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    void registerUser(Account account);
}
