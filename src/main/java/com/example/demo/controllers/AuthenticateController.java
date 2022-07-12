package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.LoginResponse;
import com.example.demo.security.JwtTokenUtils;
import com.example.demo.services.JwtUserDetailService;
import com.example.demo.utils.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthenticateController {
    private final Utilities utilities;

    private final JwtTokenUtils jwtTokenUtils;

    private final JwtUserDetailService jwtUserDetailService;


    public AuthenticateController(Utilities utilities, JwtTokenUtils jwtTokenUtils, JwtUserDetailService jwtUserDetailService) {
        this.utilities = utilities;
        this.jwtTokenUtils = jwtTokenUtils;
        this.jwtUserDetailService = jwtUserDetailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (utilities.isEmpty(username) || utilities.isEmpty(password)) {
            ResponseEntity.badRequest().body("Please provide username and password");
        }
        utilities.authenticate(username, password);
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);
        String token = jwtTokenUtils.generateToken(userDetails);
        Date expiredDate =  jwtTokenUtils.getExpirationDate(token);
        return ResponseEntity.ok(new LoginResponse(token, expiredDate));
    }

    @PostMapping("/regis")
    public ResponseEntity<?> regis(@RequestBody Account account) {
        jwtUserDetailService.registerUser(account);
        return ResponseEntity.ok("Success");
    }
}
