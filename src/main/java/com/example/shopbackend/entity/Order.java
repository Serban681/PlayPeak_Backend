package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name="shop_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="shop_user_id", nullable=false)
    private User user;
    @OneToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name="delivery_address_id", nullable=false)
    private Address deliveryAddress;
    @ManyToOne
    @JoinColumn(name="billing_address_id", nullable=false)
    private Address billingAddress;
    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate orderDate;
}
