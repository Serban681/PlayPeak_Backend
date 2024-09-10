package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductAttributeAndAttributeValuesDto {
    private int id;
    private ProductAttributeDto attribute;
    private List<ProductAttributeValueDto> values;
}
