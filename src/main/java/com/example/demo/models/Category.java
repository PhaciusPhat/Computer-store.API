package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name="Category")
public class Category {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(unique = true)
    @Size(min = 2, message = "Name must at least be at least 2 characters")
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
    @Column(columnDefinition = "boolean default false")
    private boolean isDisabled;

    public Category(String name, boolean isDisabled) {
        this.name = name;
        this.isDisabled = isDisabled;
    }

    @JsonIgnore
    @OneToMany(mappedBy="category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
}
