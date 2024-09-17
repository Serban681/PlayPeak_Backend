package com.example.shopbackend.dto;

import com.example.shopbackend.entity.ProductAttribute;
import com.example.shopbackend.entity.ProductAttributeValue;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ProductAttributeAndValueDto {
    private int id;
    private ProductAttributeDto attribute;
    private ProductAttributeValueDto attributeValue;
}
