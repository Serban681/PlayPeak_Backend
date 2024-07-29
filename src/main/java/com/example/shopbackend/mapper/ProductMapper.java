package com.example.shopbackend.mapper;

import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    @Autowired
    CategoryMapper categoryMapper;
    public ProductDto productToProductDto(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getAvailableQuantity(),
            product.getAddedDate(),
            categoryMapper.categoryToCategoryDto(product.getCategory()),
            product.getAttributeAndAttributeValues()
        );
    }
}
