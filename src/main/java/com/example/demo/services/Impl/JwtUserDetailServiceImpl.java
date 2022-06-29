package com.example.demo.services.Impl;

import com.example.demo.enums.Role;
import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.services.JwtUserDetailService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
@Service
public class JwtUserDetailServiceImpl implements JwtUserDetailService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public JwtUserDetailServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null){
            throw new NotFoundException("User not found");
        } else{
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(account.getRole().name()));
            return new org.springframework.security.core
                    .userdetails.User(account.getUsername(), account.getPassword(), authorities);
        }
    }

    @Override
    public void registerUser(Account account) {
        Account isExists = accountRepository.findByUsername(account.getUsername());
        if(isExists != null) {
            throw new BadRequestException("User already exists");
        } else{
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setIsDisabled(false);
            account.setIsActive(false);
            account.setRole(Role.USER);
            accountRepository.save(account);
        }
    }
}
