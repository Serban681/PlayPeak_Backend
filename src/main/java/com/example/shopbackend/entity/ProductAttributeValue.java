package com.example.shopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_attribute_value")
public class ProductAttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_attribute_value_id")
    private int id;
    @Column(nullable = false)
    private String value;
}
