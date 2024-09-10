package com.example.shopbackend.service;

import com.example.shopbackend.dto.ProductAttributeDto;
import com.example.shopbackend.entity.ProductAttribute;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductAttributeMapper;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductAttributeRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeService {
    private final ProductAttributeMapper productAttributeMapper;
    private final ProductAttributeRepository productAttributeRepository;
    public ProductAttributeService(
            ProductAttributeMapper productAttributeMapper,
            ProductAttributeRepository productAttributeRepository
            ) {
        this.productAttributeMapper = productAttributeMapper;
        this.productAttributeRepository = productAttributeRepository;
    }

    public ProductAttributeDto create(ProductAttributeDto productAttributeDto) {
        ProductAttribute productAttribute = productAttributeRepository.findProductAttributeByName(productAttributeDto.getName());

        if(productAttribute != null) {
            return productAttributeMapper.toDto(productAttribute);
        }

        ProductAttribute newProductAttribute = productAttributeMapper.toEntity(productAttributeDto);
        ProductAttribute savedProductAttribute = productAttributeRepository.save(newProductAttribute);
        return productAttributeMapper.toDto(savedProductAttribute);
    }
}
