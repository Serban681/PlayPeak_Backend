package com.example.shopbackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name="shop_user_id", nullable=false)
    private User user;
    @OneToMany(mappedBy="id")
    private List<CartEntry> cartEntries;
    @Transient
    private float totalPrice = 0;
}
