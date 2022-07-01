package com.example.demo.models;

import com.example.demo.models.ManyToManyPrimaryKey.RatingKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Rating")
@EqualsAndHashCode(callSuper = true)
public class Rating extends Auditable{
    @JsonIgnore
    @EmbeddedId
    private RatingKey ratingKey;
    private int stars;
    private String comment;

    @MapsId("accountId")
    @JoinColumn(name="accountId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    @JsonIgnore
    @MapsId("productId")
    @JoinColumn(name="productId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
