package com.example.shopbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="product_attribute_and_value")
public class ProductAttributeAndValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="product_attribute_id", nullable=false)
    private ProductAttribute attribute;
    @ManyToOne
    @JoinColumn(name="product_attribute_value_id", nullable=false)
    private ProductAttributeValue attributeValue;
}
