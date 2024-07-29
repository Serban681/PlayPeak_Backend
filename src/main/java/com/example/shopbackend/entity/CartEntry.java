package com.example.shopbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_entry")
public class CartEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_variance_id", nullable = false)
    private ProductVariance productVariance;
    @Column(nullable = false)
    private int quantity = 0;
    @Transient
    private float pricePerPiece;
    @Transient
    private float totalPrice;
}
