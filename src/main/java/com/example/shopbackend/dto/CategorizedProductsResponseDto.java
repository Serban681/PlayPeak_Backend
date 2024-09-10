package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class CategorizedProductsResponseDto {
    private String category;
    private List<ProductRequest> products;
}
