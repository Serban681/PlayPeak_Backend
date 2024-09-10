package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.ProductAttributeValueDto;
import com.example.shopbackend.entity.ProductAttributeValue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAttributeValueMapper {
    public ProductAttributeValueDto toDto(ProductAttributeValue productAttributeValue);
    public ProductAttributeValue toEntity(ProductAttributeValueDto productAttributeValueDto);
}
