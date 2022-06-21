package com.example.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Data
@Service
@NoArgsConstructor
@AllArgsConstructor
public class Utilities {
    @Autowired
    private AuthenticationManager authenticationManager;

    public void authenticate(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,  password));
        } catch (DisabledException e) {
            System.out.println("User is disabled");
//            throw new BadRequestException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials");
//            throw new BadRequestException("INVALID_CREDENTIALS");
        }
    }

    public boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
