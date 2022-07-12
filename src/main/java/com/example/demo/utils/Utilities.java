package com.example.demo.utils;

import com.example.demo.exception.BadRequestException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Data
@Service
@NoArgsConstructor
public class Utilities {
    private AuthenticationManager authenticationManager;

    @Autowired
    public Utilities(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void authenticate(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,  password));
        } catch (DisabledException e) {
            System.out.println("User is disabled");
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials");
            throw new BadRequestException("Bad credentials");
        }
    }

    public boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public Pageable createPageable(Integer page, Integer size, String sort) {
        return getPageable(page, size, sort);
    }

    private Pageable getPageable(Integer page, Integer size, String sort) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        assert sortable != null;
        return PageRequest.of(page, size, sortable);
    }

    public String encrypt(String strToEncrypt, String myKey) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt, String myKey) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
