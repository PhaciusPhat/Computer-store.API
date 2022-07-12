package com.example.demo.controllers.admin;


import com.example.demo.models.Account;
import com.example.demo.services.AccountService;
import com.example.demo.utils.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/admin/account")
public class AccountController {
    private final AccountService accountService;
    private final Utilities utilities;

    public AccountController(AccountService accountService, Utilities utilities) {
        this.accountService = accountService;
        this.utilities = utilities;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ) {
        return ResponseEntity.ok(accountService.findAllDTO(utilities.createPageable(page, size, sort)));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.findDTOById(id));
    }


    @PostMapping
    public ResponseEntity<?> save(
            @RequestBody Account account
    ) {
        return ResponseEntity.ok(accountService.save(account));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> activeAccount(@PathVariable UUID id) {
        accountService.activeAccount(id);
        return ResponseEntity.ok("active success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        accountService.delete(id);
        return ResponseEntity.ok("delete success");
    }

}
