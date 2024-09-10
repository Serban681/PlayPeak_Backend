package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.ProductAttributeAndValueDto;
import com.example.shopbackend.entity.ProductAttributeAndValue;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeAndValueMapper {
    private final ProductAttributeMapper productAttributeMapper;
    private final ProductAttributeValueMapper productAttributeValueMapper;

    public ProductAttributeAndValueMapper(ProductAttributeMapper productAttributeMapper, ProductAttributeValueMapper productAttributeValueMapper) {
        this.productAttributeMapper = productAttributeMapper;
        this.productAttributeValueMapper = productAttributeValueMapper;
    }

    public ProductAttributeAndValueDto toDto(ProductAttributeAndValue productAttributeAndValue) {
        ProductAttributeAndValueDto productAttributeAndValueDto = new ProductAttributeAndValueDto();
        productAttributeAndValueDto.setId(productAttributeAndValue.getId());
        productAttributeAndValueDto.setName(productAttributeAndValue.getAttribute().getName());
        productAttributeAndValueDto.setValue(productAttributeAndValue.getAttributeValue().getValue());
        return productAttributeAndValueDto;
    }

    public ProductAttributeAndValue toEntity(ProductAttributeAndValueDto productAttributeAndValueDto) {
        ProductAttributeAndValue productAttributeAndValue = new ProductAttributeAndValue();
        productAttributeAndValue.setId(productAttributeAndValueDto.getId());
        /// TODO: fix this
//        productAttributeAndValue.setAttribute(productAttributeAndValue);
        return productAttributeAndValue;
    }
}
