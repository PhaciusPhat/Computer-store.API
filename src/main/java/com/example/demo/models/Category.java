package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name="Category")
public class Category {
    @Id
    private UUID id;
    private String name;
    private String urlImage;

    public Category(String name, String urlImage) {
        this.name = name;
        this.urlImage = urlImage;
    }

    @JsonIgnore
    @OneToMany(mappedBy="category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
}
