package com.example.shopbackend.entity;

import jakarta.persistence.*;

import java.util.Date;

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
    @JoinColumn(name="invoice_address_id", nullable=false)
    private Address invoiceAddress;
    @Column(name="total_price", nullable=false)
    private float totalPrice;
    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;
}
