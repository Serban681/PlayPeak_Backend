package com.example.shopbackend.dto;

import com.example.shopbackend.entity.Category;
import com.example.shopbackend.entity.ProductAttributeAndAttributeValues;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ToString
@Data
public class ProductDto {
    private int id;
    private String name;
    private boolean inStock;
    private float price;
    private String photoUrl;
    private LocalDate addedDate;
    private CategoryDto category;
    private List<ProductAttributeAndAttributeValuesDto> attributesAndAttributeValues;
}
