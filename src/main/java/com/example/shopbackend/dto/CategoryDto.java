package com.example.shopbackend.dto;

public class CategoryDto {
    private int id;
    private String name;
    private String description;
    public CategoryDto(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
