package com.example.demo.services.Impl;

import com.example.demo.enums.Role;
import com.example.demo.exception.BadRequestException;
import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.response.dto.AccountDTO;
import com.example.demo.services.AccountService;
import com.example.demo.services.EmailService;
import com.example.demo.utils.Utilities;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Value("${jwt.secret_key}")
    private String secretKey;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AccountRepository accountRepository;
    private final Utilities utilities;
    private final ModelMapper modelMapper;

    private final EmailService emailService;

    public AccountServiceImpl(AccountRepository accountRepository, Utilities utilities, ModelMapper modelMapper, EmailService emailService) {
        this.accountRepository = accountRepository;
        this.utilities = utilities;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    private AccountDTO convertToDto(Account account) {
        return account != null ? modelMapper.map(account, AccountDTO.class) : null;

    }

    private Account findById(UUID id) {
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
    }

    private Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    private String getLocalUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails != null) {
            return userDetails.getUsername();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @SneakyThrows
    private boolean checkAccount(String username) {
        return !Objects.equals(getLocalUsername(), username);
    }

    @Override
    public Page<AccountDTO> findAllDTO(Pageable pageable) {
        return accountRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    public AccountDTO findDTOById(UUID id) {
        return convertToDto(accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found")));
    }

    @Override
    @SneakyThrows
    public AccountDTO findDTOByUsername() {
        return convertToDto(accountRepository.findByUsername(getLocalUsername()));
    }

    @Override
    public Account findLocalAccountByUsername() {
        return accountRepository.findByUsername(getLocalUsername());
    }

    @Override
    public Account save(Account account) {
        Account accountExist = accountRepository.
                findByUsername(account.getUsername());
        if (accountExist != null) {
            throw new BadRequestException("Username is exist");
        }
        accountExist = accountRepository.findByEmail(account.getEmail());
        if (accountExist != null) {
            throw new BadRequestException("Email is exist");
        }
        account.setIsActive(false);
        account.setIsDisabled(false);
        account.setRole(Role.USER);
        return accountRepository.save(account);
    }


    @Override
    public void updateAccountInformation(Account account) {
        Account accountExist = accountRepository.findByUsername(account.getUsername());
        if (!checkAccount(accountExist.getUsername())) {
            accountExist.setName(account.getName());
            accountExist.setEmail(account.getEmail());
            accountExist.setPhone(account.getPhone());
            accountRepository.save(accountExist);
        } else {
            throw new BadRequestException("You can't update different account");
        }
    }

    @Override
    public void updateAccountPassword(String username, String password, String newPassword) {
        Account account = findByUsername(username);
        if (account == null) {
            throw new NotFoundException("Account not found");
        }
        if (!checkAccount(username)) {
            utilities.authenticate(username, password);
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);

        } else {
            throw new BadRequestException("You can't update different account");
        }
    }

    @Override
    @SneakyThrows
    public String requestActiveAccount() {
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        String randomNumber = String.format("%04d", number);
        String username = getLocalUsername();
        long timestamp = new Timestamp(new Date(System.currentTimeMillis() + 15 * 60 * 1000).getTime()).getTime();
        String encryptString = username + "-" + randomNumber + "-" + timestamp;
        //gửi code về mail user
        Account account = findByUsername(username);
        if(account.getIsActive()){
            throw new BadRequestException("Account is active");
        }
        emailService.sendMail(account.getEmail(), "Active account", "Your code is: " + randomNumber);
        return utilities.encrypt(encryptString, secretKey);
    }

    @Override
    public void activeAccount(String code, String encryptString) {
        long timestamp = new Timestamp(new Date(System.currentTimeMillis()).getTime()).getTime();
        String decryptString = utilities.decrypt( encryptString, secretKey);
        String[] split = decryptString.split("-");
        if (split.length == 3) {
            String username = split[0];
            String codeActive = split[1];
            long time = Long.parseLong(split[2]);
            if (timestamp - time < 15 * 60 * 1000) {
                Account account = findByUsername(username);
                if (account.getIsActive()) {
                    throw new BadRequestException("Account is already active");
                } else {
                    if (codeActive.equals(code)) {
                        account.setIsActive(true);
                        accountRepository.save(account);
                    } else {
                        throw new BadRequestException("Code is incorrect");
                    }
                }
            } else {
                throw new BadRequestException("Code is expired");
            }
        } else {
            throw new BadRequestException("Code is incorrect");
        }
    }

    @Override
    public void activeAccount(UUID id) {
        Account account = findById(id);
        if (checkAccount(account.getUsername())) {
            account.setIsActive(true);
            accountRepository.save(account);
        } else {
            throw new BadRequestException("You can't update your account");
        }
    }

    @Override
    public void delete(UUID id) {
        Account account = findById(id);
        if (checkAccount(account.getUsername())) {
            account.setIsDisabled(!account.getIsDisabled());
            accountRepository.save(account);
        } else {
            throw new BadRequestException("You can't disable your account");
        }
    }
}
