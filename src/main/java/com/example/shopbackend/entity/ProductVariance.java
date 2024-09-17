package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
@Table(name="product_variance")
public class ProductVariance {
    @Id
    @Column(name="product_variance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    @OneToMany
    @JoinColumn(name="product_variance_id")
    private List<ProductAttributeAndValue> attributesAndValues;
    @Column(nullable = false)
    private int quantity = 0;
}
