package com.example.shopbackend.mapper;

import com.example.shopbackend.dto.CategoryDto;
import com.example.shopbackend.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryDto categoryToCategoryDto(Category category) {
        return new CategoryDto(
            category.getId(),
            category.getName(),
            category.getDescription()
        );
    }
}
