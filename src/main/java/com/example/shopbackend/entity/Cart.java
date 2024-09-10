package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name="shop_user_id")
    private User user;
    @OneToMany
    @JoinColumn(name="cart_id")
    private List<CartEntry> cartEntries;
    private float totalPrice = 0;
}
