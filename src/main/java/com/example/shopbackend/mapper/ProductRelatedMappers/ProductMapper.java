package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.CategoryDto;
import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.dto.ProductRequest;
import com.example.shopbackend.entity.Product;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductMapper {
    private final CategoryMapper categoryMapper;
    private final ProductAttributeAndAttributeValuesMapper productAttributeAndAttributeValuesMapper;
    public ProductMapper(
            CategoryMapper categoryMapper,
            ProductAttributeAndAttributeValuesMapper productAttributeAndAttributeValuesMapper) {
        this.categoryMapper = categoryMapper;
        this.productAttributeAndAttributeValuesMapper = productAttributeAndAttributeValuesMapper;
    }

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setPhotoUrl(product.getPhotoUrl());
        productDto.setAddedDate(product.getAddedDate());
        productDto.setCategory(categoryMapper.toDto(product.getCategory()));
        productDto.setAttributesAndAttributeValues(product.getAttributesAndAttributeValues().stream().map(productAttributeAndAttributeValuesMapper::toDto).collect(Collectors.toList()));
        productDto.setInStock(product.isInStock());

        return productDto;
    }

    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPhotoUrl(productDto.getPhotoUrl());
        product.setAddedDate(productDto.getAddedDate());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryMapper.toEntity(productDto.getCategory()));
        product.setAttributesAndAttributeValues(productDto.getAttributesAndAttributeValues().stream().map(productAttributeAndAttributeValuesMapper::toEntity).collect(Collectors.toList()));
        product.setInStock(productDto.isInStock());

        return product;
    }

    public ProductRequest dtoToRequest(ProductDto productDto) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(productDto.getId());
        productRequest.setName(productDto.getName());
        productRequest.setPrice(productDto.getPrice());
        productRequest.setPhotoUrl(productDto.getPhotoUrl());
        productRequest.setAddedDate(productDto.getAddedDate());
        productRequest.setCategory(productDto.getCategory().getName());
        productRequest.setAttributesAndAttributeValues(
                productDto.getAttributesAndAttributeValues()
                        .stream()
                        .map(productAttributeAndAttributeValuesMapper::dtoToRequest)
                        .collect(Collectors.toList()));
        productRequest.setInStock(productDto.isInStock());
        return productRequest;
    }

    public ProductDto requestToDto(ProductRequest productRequest) {
        ProductDto productDto = new ProductDto();
        productDto.setId(productRequest.getId());
        productDto.setName(productRequest.getName());
        productDto.setPrice(productRequest.getPrice());
        productDto.setPhotoUrl(productRequest.getPhotoUrl());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(productRequest.getCategory());
        productDto.setCategory(categoryDto);
        productDto.setInStock(productRequest.isInStock());
        productDto.setAddedDate(productRequest.getAddedDate());

        productDto.setAttributesAndAttributeValues(
                productRequest.getAttributesAndAttributeValues()
                        .stream()
                        .map(productAttributeAndAttributeValuesMapper::requestToDto)
                        .collect(Collectors.toList()));
        return productDto;
    }
}
