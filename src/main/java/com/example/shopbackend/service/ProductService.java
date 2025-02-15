package com.example.shopbackend.service;

import com.example.shopbackend.dto.*;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.entity.ProductAttributeAndAttributeValues;
import com.example.shopbackend.entity.ProductSortMethod;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductAttributeAndAttributeValuesMapper;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductMapper;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductAttributeAndAttributeValuesRepository;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductAttributesAndAttributeValuesService productAttributeAndAttributeValuesService;
    private final ProductAttributeAndAttributeValuesMapper productAttributeAndAttributeValuesMapper;
    private final CategoryService categoryService;
    private final ProductVarianceService productVarianceService;

    public ProductService(
            ProductMapper productMapper,
            ProductRepository productRepository,
            ProductAttributesAndAttributeValuesService productAttributeAndAttributeValuesService,
            ProductAttributeAndAttributeValuesMapper productAttributeAndAttributeValuesMapper,
            CategoryService categoryService,
            ProductVarianceService productVarianceService) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.productAttributeAndAttributeValuesService = productAttributeAndAttributeValuesService;
        this.productAttributeAndAttributeValuesMapper = productAttributeAndAttributeValuesMapper;
        this.categoryService = categoryService;
        this.productVarianceService = productVarianceService;
    }

    public List<CategorizedProductsResponseDto> getAll(SearchFiltersDto searchFiltersDto) {
        switch (ProductSortMethod.valueOf(searchFiltersDto.getSortMethod())) {
            case PRICE_ASC -> {
                List<ProductRequest> products = productRepository.findAllByOrderByPriceAsc().stream().map(product -> productMapper.dtoToRequest(productMapper.toDto(product))).toList();

                return filterProductsBasedOnCategory(products, searchFiltersDto.getCategories());
            }
            case PRICE_DESC -> {
                List<ProductRequest> products = productRepository.findAllByOrderByPriceDesc().stream().map(product -> productMapper.dtoToRequest(productMapper.toDto(product))).collect(Collectors.toList());

                return filterProductsBasedOnCategory(products, searchFiltersDto.getCategories());
            }
            case NAME_ASC -> {
                List<ProductRequest> products = productRepository.findAllByOrderByNameAsc().stream().map(product -> productMapper.dtoToRequest(productMapper.toDto(product))).collect(Collectors.toList());

                return filterProductsBasedOnCategory(products, searchFiltersDto.getCategories());
            }
            case NAME_DESC -> {
                List<ProductRequest> products = productRepository.findAllByOrderByNameDesc().stream().map(product -> productMapper.dtoToRequest(productMapper.toDto(product))).collect(Collectors.toList());

                return filterProductsBasedOnCategory(products, searchFiltersDto.getCategories());
            }
            case NEWEST -> {
                List<ProductRequest> products = productRepository.findAllByOrderByAddedDateDesc().stream().map(product -> productMapper.dtoToRequest(productMapper.toDto(product))).collect(Collectors.toList());

                return filterProductsBasedOnCategory(products, searchFiltersDto.getCategories());
            }
            case OLDEST -> {
                List<ProductRequest> products = productRepository.findAllByOrderByAddedDate().stream().map(product -> productMapper.dtoToRequest(productMapper.toDto(product))).collect(Collectors.toList());

                return filterProductsBasedOnCategory(products, searchFiltersDto.getCategories());
            }
            default -> {
                List<ProductRequest> products = productRepository.findAll().stream().map(product -> productMapper.dtoToRequest(productMapper.toDto(product))).collect(Collectors.toList());

                return filterProductsBasedOnCategory(products, searchFiltersDto.getCategories());
            }
        }
    }

    public ProductRequest getOneById(int id) {
        return productMapper.dtoToRequest(productMapper.toDto(productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product", id))));
    }

    public ProductRequest create(ProductRequest productRequest) {
        List<Product> products = productRepository.findProductsByName(productRequest.getName());

        productRequest.setAddedDate(LocalDate.now());

        ProductDto productDto = productMapper.requestToDto(productRequest);

        CategoryDto categoryDto = categoryService.create(productDto.getCategory());

        productDto.setCategory(categoryDto);

        Product productToSave = productMapper.toEntity(productDto);

        List<ProductAttributeAndAttributeValues> productAttributesAndAttributeValues = productDto.getAttributesAndAttributeValues()
                .stream()
                .map(obj -> productAttributeAndAttributeValuesMapper.toEntity(productAttributeAndAttributeValuesService.create(obj)))
                .toList();

        productToSave.setAttributesAndAttributeValues(productAttributesAndAttributeValues);

        ProductDto savedProduct = productMapper.toDto(productRepository.save(productToSave));

        productVarianceService.create(savedProduct);

        return productMapper.dtoToRequest(savedProduct);
    }

    public List<ProductRequest> createMany(List<ProductRequest> productRequests) {
        List<ProductRequest> savedProducts = new ArrayList<>();

        for (ProductRequest productRequest : productRequests) {
            savedProducts.add(create(productRequest));
        }

        return savedProducts;
    }

    public void delete(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product", id));
        List<ProductAttributeAndAttributeValues> attributesAndAttributeValues = product.getAttributesAndAttributeValues();
        productAttributeAndAttributeValuesService.deleteAll(attributesAndAttributeValues.stream().map(productAttributeAndAttributeValuesMapper::toDto).toList());
        productVarianceService.deleteVariancesByProductId(id);
        productRepository.delete(product);
    }

    private List<CategorizedProductsResponseDto> filterProductsBasedOnCategory(List<ProductRequest> products, String[] categories) {
        List<CategorizedProductsResponseDto> categorizedProducts = new ArrayList<>();

        if(categories.length == 0) {
            CategorizedProductsResponseDto categorizedProductsResponseDto = new CategorizedProductsResponseDto();
            categorizedProductsResponseDto.setCategory("All");
            categorizedProductsResponseDto.setProducts(products);
            categorizedProducts.add(categorizedProductsResponseDto);
            return categorizedProducts;
        }

        for (String category : categories) {
            CategorizedProductsResponseDto categorizedProductsResponseDto = new CategorizedProductsResponseDto();
            categorizedProductsResponseDto.setCategory(category);
            categorizedProductsResponseDto.setProducts(products.stream().filter(product -> product.getCategory().equals(category)).collect(Collectors.toList()));
            categorizedProducts.add(categorizedProductsResponseDto);
        }

        return categorizedProducts;
    }
}
