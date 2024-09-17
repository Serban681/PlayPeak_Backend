package com.example.shopbackend.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ToString
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable=false)
    private String photoUrl;
    @Column(nullable = false)
    private float price;
//    private int availableQuantity = 0;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate addedDate;
    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
    @OneToMany
    @JoinColumn(name="product_id")
    private List<ProductAttributeAndAttributeValues> attributesAndAttributeValues;
}
