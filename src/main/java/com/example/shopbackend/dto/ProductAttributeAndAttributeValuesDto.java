package com.example.shopbackend.dto;

import java.util.List;

public class ProductAttributeAndAttributeValuesDto {
    private int id;
    private String name;
    private List<String> values;
    public ProductAttributeAndAttributeValuesDto(int id, String name, List<String> values) {
        this.id = id;
        this.name = name;
        this.values = values;
    }
    public int getId() {
       return this.id;
    }
    public String getName() {
       return this.name;
    }
    public List<String> getValues() {
        return this.values;
    }
}
