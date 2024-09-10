package com.example.shopbackend.service;

import com.example.shopbackend.dto.ProductAttributeAndAttributeValuesDto;
import com.example.shopbackend.entity.ProductAttributeAndAttributeValues;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductAttributeAndAttributeValuesMapper;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductAttributeAndAttributeValuesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductAttributesAndAttributeValuesService {
    private final ProductAttributeService productAttributeService;
    private final ProductAttributeValueService productAttributeValueService;
    private final ProductAttributeAndAttributeValuesRepository productAttributeAndAttributeValuesRepository;
    private final ProductAttributeAndAttributeValuesMapper productAttributeAndAttributeValuesMapper;

    public ProductAttributesAndAttributeValuesService(
            ProductAttributeService productAttributeService,
            ProductAttributeValueService productAttributeValueService,
            ProductAttributeAndAttributeValuesRepository productAttributeAndAttributeValuesRepository,
            ProductAttributeAndAttributeValuesMapper productAttributeAndAttributeValuesMapper
            ) {
        this.productAttributeService = productAttributeService;
        this.productAttributeValueService = productAttributeValueService;
        this.productAttributeAndAttributeValuesRepository = productAttributeAndAttributeValuesRepository;
        this.productAttributeAndAttributeValuesMapper = productAttributeAndAttributeValuesMapper;
    }

    public List<ProductAttributeAndAttributeValuesDto> getAll() {
        return StreamSupport.stream(productAttributeAndAttributeValuesRepository.findAll().spliterator(), false).map(productAttributeAndAttributeValuesMapper::toDto).collect(Collectors.toList());
    }

    public ProductAttributeAndAttributeValuesDto getOneById(int id) {
        return productAttributeAndAttributeValuesMapper.toDto(productAttributeAndAttributeValuesRepository.findProductAttributeAndAttributeValuesById(id));
    }
    @Transactional
    public ProductAttributeAndAttributeValuesDto create(ProductAttributeAndAttributeValuesDto productAttributeAndAttributeValuesDto) {
        setProductAttributeAndAttributeValuesDto(productAttributeAndAttributeValuesDto);

        ProductAttributeAndAttributeValues entity = productAttributeAndAttributeValuesRepository.save(
                productAttributeAndAttributeValuesMapper.toEntity(productAttributeAndAttributeValuesDto)
        );

        return productAttributeAndAttributeValuesMapper.toDto(entity);
    }

    public void delete(int id) {
        productAttributeAndAttributeValuesRepository.deleteById(id);
    }

    public void deleteAll(List<ProductAttributeAndAttributeValuesDto> paavDtos) {
        List<ProductAttributeAndAttributeValues> paavs = paavDtos.stream().map(productAttributeAndAttributeValuesMapper::toEntity).toList();
        productAttributeAndAttributeValuesRepository.deleteAll(paavs);
    }

    public void setProductAttributeAndAttributeValuesDto(ProductAttributeAndAttributeValuesDto productAttributeAndAttributeValuesDto) {
        productAttributeAndAttributeValuesDto.setAttribute(productAttributeService.create(productAttributeAndAttributeValuesDto.getAttribute()));
        productAttributeAndAttributeValuesDto.setValues(
                productAttributeAndAttributeValuesDto.getValues().stream().map(productAttributeValueService::create).collect(Collectors.toList())
        );
    }
}
