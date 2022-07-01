package com.example.demo.response.dto;

import com.example.demo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private UUID id;
    private String username;
    private String email;
    private String name;
    private String phone;
    private Role role;
    private Boolean isActive;
    private Boolean isDisabled;
}
