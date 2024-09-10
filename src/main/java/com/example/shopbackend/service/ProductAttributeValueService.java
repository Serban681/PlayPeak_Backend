package com.example.shopbackend.service;

import com.example.shopbackend.dto.ProductAttributeValueDto;
import com.example.shopbackend.entity.ProductAttribute;
import com.example.shopbackend.entity.ProductAttributeValue;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductAttributeValueMapper;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductAttributeValueRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeValueService {
    private final ProductAttributeValueMapper productAttributeValueMapper;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    public ProductAttributeValueService(
            ProductAttributeValueMapper productAttributeValueMapper,
            ProductAttributeValueRepository productAttributeValueRepository
    ) {
        this.productAttributeValueMapper = productAttributeValueMapper;
        this.productAttributeValueRepository = productAttributeValueRepository;
    }

    public ProductAttributeValueDto create(ProductAttributeValueDto productAttributeValueDto) {
        ProductAttributeValue productAttributeValue = productAttributeValueRepository.findProductAttributeValueByValue(productAttributeValueDto.getValue());

        if(productAttributeValue != null) {
            return productAttributeValueMapper.toDto(productAttributeValue);
        }

        return productAttributeValueMapper.toDto(productAttributeValueRepository.save(productAttributeValueMapper.toEntity(productAttributeValueDto)));
    }
}
