package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name="Brand")
public class Brand {
    @Id
    private UUID id;
    private String name;
    private String origin;
    private String urlImage;

    public Brand(String name, String origin, String urlImage) {
        this.name = name;
        this.origin = origin;
        this.urlImage = urlImage;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
}
