package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductAttributeAndValueRequest {
    private int id;
    private String name;
    private String value;
}
