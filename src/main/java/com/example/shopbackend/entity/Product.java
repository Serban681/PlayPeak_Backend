package com.example.shopbackend.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private float price;
    @Transient
    private int availableQuantity = 0;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date addedDate;
    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "id")
    private List<ProductAttributeAndAttributeValues> attributeAndAttributeValues;
    public Product() {}
    public Product(int id, String name, String description, float price, Date addedDate, Category category, List<ProductAttributeAndAttributeValues> attributeAndAttributeValues) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.addedDate = addedDate;
        this.category = category;
        this.attributeAndAttributeValues = attributeAndAttributeValues;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public float getPrice() {
        return price;
    }
    public int getAvailableQuantity() {
        return availableQuantity;
    }
    public Date getAddedDate() {
        return addedDate;
    }
    public Category getCategory() {
        return category;
    }
    public List<ProductAttributeAndAttributeValues> getAttributeAndAttributeValues() {
        return attributeAndAttributeValues;
    }
}
