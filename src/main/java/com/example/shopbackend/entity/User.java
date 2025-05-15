package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "shop_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy="id")
    private List<Order> orders;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="profile_image_url")
    private String profileImageUrl;

    @Column(nullable = false)
    private String email;

    @Column(name="phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String password;

    @Column(name="registration_date", nullable = false)
    private LocalDate registrationDate;

    @ManyToOne
    @JoinColumn(name="default_delivery_address_id", nullable = false)
    private Address default_delivery_address;

    @ManyToOne
    @JoinColumn(name="default_billing_address_id", nullable = false)
    private Address default_billing_address;

    @PrePersist
    @PreUpdate
    private void checkAddresses() {
        if(default_delivery_address != null && default_delivery_address.equals(default_billing_address)) {
            default_billing_address = default_delivery_address;
        }
    }
}
