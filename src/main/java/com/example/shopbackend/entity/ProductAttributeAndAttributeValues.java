package com.example.shopbackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product_attribute_and_attribute_values")
public class ProductAttributeAndAttributeValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="product_attribute_id", nullable=false)
    private ProductAttribute attribute;
    @ManyToMany
    @JoinTable(name="product_attribute_and_attribute_values_product_attribute_value",
            joinColumns = @JoinColumn(name="product_attribute_value_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="product_attribute_and_attribute_values_id", referencedColumnName = "id")
    )
    private List<ProductAttributeValue> attributeValues;
}
