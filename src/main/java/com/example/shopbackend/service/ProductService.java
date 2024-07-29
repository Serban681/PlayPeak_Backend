package com.example.shopbackend.service;

import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.ProductMapper;
import com.example.shopbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public List<ProductDto> getAll() {
        List<Product> products = StreamSupport.stream(productRepository.findAll().spliterator(), false).collect(Collectors.toList());
        return products.stream().map(product -> productMapper.productToProductDto(product)).collect(Collectors.toList());

    }
    public ProductDto getOneById(int id) {
        return productMapper.productToProductDto(productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product", id)));
    }
}
