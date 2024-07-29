package com.example.shopbackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="product_variance")
public class ProductVariance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    @OneToMany(mappedBy="id")
    private List<ProductAttributeAndValue> attributesValuesAndQuantity;
    @Column(nullable = false)
    private int quantity = 0;
}
