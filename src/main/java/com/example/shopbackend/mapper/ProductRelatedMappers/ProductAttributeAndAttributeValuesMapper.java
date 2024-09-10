package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.ProductAttributeAndAttributeValuesDto;
import com.example.shopbackend.dto.ProductAttributeDto;
import com.example.shopbackend.dto.ProductAttributeValueDto;
import com.example.shopbackend.dto.ProductAttributesAndAttributeValuesRequest;
import com.example.shopbackend.entity.ProductAttributeAndAttributeValues;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductAttributeAndAttributeValuesMapper {
    private final ProductAttributeMapper productAttributeMapper;
    private final ProductAttributeValueMapper productAttributeValueMapper;

    public ProductAttributeAndAttributeValuesMapper(ProductAttributeMapper productAttributeMapper, ProductAttributeValueMapper productAttributeValueMapper) {
        this.productAttributeMapper = productAttributeMapper;
        this.productAttributeValueMapper = productAttributeValueMapper;
    }

    public ProductAttributeAndAttributeValuesDto toDto(ProductAttributeAndAttributeValues productAttributeAndAttributeValues) {
        ProductAttributeAndAttributeValuesDto productAttributeAndAttributeValuesDto = new ProductAttributeAndAttributeValuesDto();

        productAttributeAndAttributeValuesDto.setId(productAttributeAndAttributeValues.getId());
        productAttributeAndAttributeValuesDto.setAttribute(productAttributeMapper.toDto(productAttributeAndAttributeValues.getAttribute()));
        productAttributeAndAttributeValuesDto.setValues(productAttributeAndAttributeValues.getAttributeValues().stream().map(productAttributeValueMapper::toDto).collect(Collectors.toList()));

        return productAttributeAndAttributeValuesDto;
    }

    public ProductAttributeAndAttributeValues toEntity(ProductAttributeAndAttributeValuesDto productAttributeAndAttributeValuesDto) {
        ProductAttributeAndAttributeValues productAttributeAndAttributeValues = new ProductAttributeAndAttributeValues();

        productAttributeAndAttributeValues.setId(productAttributeAndAttributeValuesDto.getId());
        productAttributeAndAttributeValues.setAttribute(productAttributeMapper.toEntity(productAttributeAndAttributeValuesDto.getAttribute()));
        productAttributeAndAttributeValues.setAttributeValues(productAttributeAndAttributeValuesDto.getValues().stream().map(productAttributeValueMapper::toEntity).collect(Collectors.toList()));

        return productAttributeAndAttributeValues;
    }

    public ProductAttributesAndAttributeValuesRequest dtoToRequest(ProductAttributeAndAttributeValuesDto productAttributeAndAttributeValuesDto) {
        ProductAttributesAndAttributeValuesRequest productAttributesAndAttributeValuesRequest = new ProductAttributesAndAttributeValuesRequest();
        productAttributesAndAttributeValuesRequest.setId(productAttributeAndAttributeValuesDto.getId());
        productAttributesAndAttributeValuesRequest.setName(productAttributeAndAttributeValuesDto.getAttribute().getName());
        productAttributesAndAttributeValuesRequest.setValues(productAttributeAndAttributeValuesDto.getValues().stream().map(ProductAttributeValueDto::getValue).collect(Collectors.toList()));

        return productAttributesAndAttributeValuesRequest;
    }

    public ProductAttributeAndAttributeValuesDto requestToDto(ProductAttributesAndAttributeValuesRequest productAttributesAndAttributeValuesRequest) {
        ProductAttributeAndAttributeValuesDto productAttributeAndAttributeValuesDto = new ProductAttributeAndAttributeValuesDto();

        ProductAttributeDto productAttributeDto = new ProductAttributeDto();
        productAttributeDto.setId(productAttributesAndAttributeValuesRequest.getId());
        productAttributeDto.setName(productAttributesAndAttributeValuesRequest.getName());
        productAttributeAndAttributeValuesDto.setAttribute(productAttributeDto);

        List<ProductAttributeValueDto> productAttributeValueDtos = productAttributesAndAttributeValuesRequest.getValues().stream().map(value -> {
            ProductAttributeValueDto productAttributeValueDto = new ProductAttributeValueDto();
            productAttributeValueDto.setValue(value);
            return productAttributeValueDto;
        }).toList();

        productAttributeAndAttributeValuesDto.setValues(productAttributeValueDtos);

        return productAttributeAndAttributeValuesDto;
    }
}
