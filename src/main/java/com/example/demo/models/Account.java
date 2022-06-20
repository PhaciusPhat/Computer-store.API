package com.example.demo.models;

import com.example.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name="Account")
@EqualsAndHashCode(callSuper = true)
public class Account extends Auditable{
    @Id
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String phone;
    private Role role;
    private Boolean isActive;
    private Boolean isDisabled;

    public Account(String username, String password, String email, String name, String phone, Role role, Boolean isActive, Boolean isDisabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.isActive = isActive;
        this.isDisabled = isDisabled;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;

    @JsonIgnore
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;
}
