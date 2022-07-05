package com.example.demo.controllers.user;

import com.example.demo.models.Account;
import com.example.demo.request.ActiveRequest;
import com.example.demo.request.ChangePassRequest;
import com.example.demo.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/public/account")
public class AccountClientController {
    private final AccountService accountService;

    public AccountClientController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/username")
    public ResponseEntity<?> findByUsername() {
        return ResponseEntity.ok(accountService.findDTOByUsername());
    }


    @PatchMapping("/information")
    public ResponseEntity<?> updateAccountInformation(
            @RequestBody Account account
    ) {
        accountService.updateAccountInformation(account);
        return ResponseEntity.ok("update success");
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updateAccountPassword(
            @RequestBody ChangePassRequest changePassRequest
    ) {
        accountService.updateAccountPassword(changePassRequest.getUsername(),
                changePassRequest.getOldPassword(),
                changePassRequest.getNewPassword());
        return ResponseEntity.ok("update success");
    }

    @PostMapping("/request-active")
    public ResponseEntity<?> requestActiveAccount() {
        return ResponseEntity.ok(accountService.requestActiveAccount());
    }

    @PostMapping("/active")
    public ResponseEntity<?> activeAccount(
            @RequestBody ActiveRequest activeRequest
    ) {
        accountService.activeAccount(activeRequest.getCode(), activeRequest.getEncryptString());
        return ResponseEntity.ok("active success");
    }
}
