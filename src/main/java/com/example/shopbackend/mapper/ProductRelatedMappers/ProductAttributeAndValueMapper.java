package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.ProductAttributeAndValueDto;
import com.example.shopbackend.dto.ProductAttributeAndValueRequest;
import com.example.shopbackend.entity.ProductAttribute;
import com.example.shopbackend.entity.ProductAttributeAndValue;
import com.example.shopbackend.entity.ProductAttributeValue;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeAndValueMapper {
    private final ProductAttributeMapper productAttributeMapper;
    private final ProductAttributeValueMapper productAttributeValueMapper;


    public ProductAttributeAndValueMapper(ProductAttributeMapper productAttributeMapper,
                                          ProductAttributeValueMapper productAttributeValueMapper)
    {
            this.productAttributeMapper = productAttributeMapper;
            this.productAttributeValueMapper = productAttributeValueMapper;
    }


    public ProductAttributeAndValueRequest requestToDto(ProductAttributeAndValue productAttributeAndValue) {
        ProductAttributeAndValueRequest productAttributeAndValueRequest = new ProductAttributeAndValueRequest();
        productAttributeAndValueRequest.setId(productAttributeAndValue.getId());
        productAttributeAndValueRequest.setName(productAttributeAndValue.getAttribute().getName());
        productAttributeAndValueRequest.setValue(productAttributeAndValue.getAttributeValue().getValue());
        return productAttributeAndValueRequest;
    }

    public ProductAttributeAndValue requestToEntity(ProductAttributeAndValueRequest productAttributeAndValueRequest) {
        ProductAttributeAndValue productAttributeAndValue = new ProductAttributeAndValue();
        productAttributeAndValue.setId(productAttributeAndValueRequest.getId());

        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setName(productAttributeAndValueRequest.getName());

        ProductAttributeValue productAttributeValue = new ProductAttributeValue();
        productAttributeValue.setValue(productAttributeAndValueRequest.getValue());

        productAttributeAndValue.setAttribute(productAttribute);
        productAttributeAndValue.setAttributeValue(productAttributeValue);

        return productAttributeAndValue;
    }

    public ProductAttributeAndValueRequest entityToRequest(ProductAttributeAndValue productAttributeAndValue) {
        ProductAttributeAndValueRequest productAttributeAndValueRequest = new ProductAttributeAndValueRequest();
        productAttributeAndValueRequest.setId(productAttributeAndValue.getId());

        productAttributeAndValueRequest.setName(productAttributeAndValue.getAttribute().getName());
        productAttributeAndValueRequest.setValue(productAttributeAndValue.getAttributeValue().getValue());

        return productAttributeAndValueRequest;
    }

    public ProductAttributeAndValue dtoToEntity(ProductAttributeAndValueDto dto) {
        ProductAttributeAndValue productAttributeAndValue = new ProductAttributeAndValue();
        productAttributeAndValue.setId(dto.getId());
        productAttributeAndValue.setAttribute(productAttributeMapper.toEntity(dto.getAttribute()));
        productAttributeAndValue.setAttributeValue(productAttributeValueMapper.toEntity(dto.getAttributeValue()));

        return productAttributeAndValue;
    }

    public ProductAttributeAndValueDto entityToDto(ProductAttributeAndValue productAttributeAndValue) {
        ProductAttributeAndValueDto productAttributeAndValueDto = new ProductAttributeAndValueDto();
        productAttributeAndValueDto.setId(productAttributeAndValue.getId());
        productAttributeAndValueDto.setAttribute(productAttributeMapper.toDto(productAttributeAndValue.getAttribute()));
        productAttributeAndValueDto.setAttributeValue(productAttributeValueMapper.toDto(productAttributeAndValue.getAttributeValue()));

        return productAttributeAndValueDto;
    }
}
