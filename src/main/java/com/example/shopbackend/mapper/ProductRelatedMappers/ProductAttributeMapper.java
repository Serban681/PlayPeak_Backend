package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.ProductAttributeDto;
import com.example.shopbackend.entity.ProductAttribute;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    ProductAttributeDto toDto(ProductAttribute productAttributeDto);
    ProductAttribute toEntity(ProductAttributeDto productAttributeDto);
}
