package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.CategoryDto;
import com.example.shopbackend.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
}
