package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class ProductAttributesAndAttributeValuesRequest {
    private int id;
    private String name;
    private List<String> values;
}
