package com.example.shopbackend.dto;

import com.example.shopbackend.entity.ProductAttributeAndAttributeValues;

import java.util.Date;
import java.util.List;

public class ProductDto {
    private int id;
    private String name;
    private String description;
    private float price;
    private int availableQuantity;
    private Date addedDate;
    private CategoryDto category;
    private List<ProductAttributeAndAttributeValues> attributeAndAttributeValues;
    public ProductDto(
        int id,
        String name,
        String description,
        float price,
        int availableQuantity,
        Date addedDate,
        CategoryDto category,
        List<ProductAttributeAndAttributeValues> attributeAndAttributeValues
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableQuantity = availableQuantity;
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
    public CategoryDto getCategory() {
        return category;
    }
    public List<ProductAttributeAndAttributeValues> getAttributeAndAttributeValues() {
        return attributeAndAttributeValues;
    }
}
