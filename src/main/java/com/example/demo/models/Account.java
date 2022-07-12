package com.example.demo.models;

import com.example.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity(name="Account")
public class Account extends Auditable{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(unique = true)
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;
    @Size(min = 1, message = "Password must be at least 1 characters")
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;
    @Column(unique = true)
    @Size(min = 5, message = "Email must be between 5 and 15 characters")
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min = 2, message = "Name must at least be at least 2 characters")
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
    @Size(min = 10, max = 10, message = "Phone must at least be 10 characters")
    private String phone;
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private Boolean isActive;

    @Column(columnDefinition = "boolean default false")
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
