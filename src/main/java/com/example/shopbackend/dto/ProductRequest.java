package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
public class ProductRequest {
    private int id;
    private String name;
    private float price;
    private String photoUrl;
    private boolean inStock;
    private LocalDate addedDate;
    private String category;
    private List<ProductAttributesAndAttributeValuesRequest> attributesAndAttributeValues;
}
