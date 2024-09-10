package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductVarianceDto {
    private int id;
    private ProductDto product;
    private List<ProductAttributeAndValueDto> attributesAndValues;
    private int quantity;
}
