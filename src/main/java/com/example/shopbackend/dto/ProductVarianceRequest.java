package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductVarianceRequest {
    private int id;
    private ProductRequest product;
    private List<ProductAttributeAndValueRequest> attributesAndValues;
    private int quantity = 0;
}
