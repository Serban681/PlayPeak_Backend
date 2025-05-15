package com.example.shopbackend.service;

import com.example.shopbackend.dto.*;
import com.example.shopbackend.entity.*;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductAttributeAndValueMapper;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductMapper;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductVarianceMapper;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductAttributeAndValueRepository;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductVarianceRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductVarianceService {
    private final ProductVarianceRepository productVarianceRepository;
    private final ProductAttributeAndValueRepository productAttributeAndValueRepository;
    private final ProductAttributeAndValueMapper productAttributeAndValueMapper;
    private final ProductVarianceMapper productVarianceMapper;
    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductVarianceService(
            ProductVarianceRepository productVarianceRepository,
            ProductAttributeAndValueRepository productAttributeAndValueRepository,
            ProductAttributeAndValueMapper productAttributeAndValueMapper,
            ProductVarianceMapper productVarianceMapper,
            ProductMapper productMapper,
            @Lazy ProductService productService
    ) {
        this.productVarianceRepository = productVarianceRepository;
        this.productAttributeAndValueRepository = productAttributeAndValueRepository;
        this.productAttributeAndValueMapper = productAttributeAndValueMapper;
        this.productVarianceMapper = productVarianceMapper;
        this.productMapper = productMapper;
        this.productService = productService;
    }

    public List<ProductVarianceRequest> getAll() {
        return productVarianceRepository.findAllByOrderByIdAsc().stream().map(productVarianceMapper::entityToRequest).toList();
    }

    public List<ProductVarianceRequest> getByProductId(int productId) {
        return productVarianceRepository.findByProductIdOrderByIdAsc(productId).stream().map(productVarianceMapper::entityToRequest).toList();
    }

    public void create(ProductDto productDto) {
        generateVariations(productDto);
    }

    public ProductVarianceRequest updateQuantity(int id, int quantity) {
        ProductVariance productVariance = productVarianceRepository.findById(id);

        if(productVariance == null) {
            throw new EntityNotFoundException("Product Variance", id);
        }

        productVariance.setQuantity(quantity);

        var response = productVarianceMapper.entityToRequest(productVarianceRepository.save(productVariance));

        productService.checkStocksStillAvailable(productVariance.getProduct().getId());

        return response;
    }

    public ProductVarianceRequest addQuantity(int id, int quantity) {
        ProductVariance productVariance = productVarianceRepository.findById(id);

        if(productVariance == null) {
            throw new EntityNotFoundException("Product Variance", id);
        }

        if(quantity > 0) {
            int curQuantity = productVariance.getQuantity();
            productVariance.setQuantity(curQuantity + quantity);

            var response = productVarianceMapper.entityToRequest(productVarianceRepository.save(productVariance));

            productService.checkStocksStillAvailable(productVariance.getProduct().getId());

            return response;
        }

        return productVarianceMapper.entityToRequest(productVariance);
    }

    public ProductVarianceRequest subtractQuantity(int id, int quantity) {
        ProductVariance productVariance = productVarianceRepository.findById(id);

        if(productVariance == null) {
            throw new EntityNotFoundException("Product Variance", id);
        }

        int curQuantity = productVariance.getQuantity();
        productVariance.setQuantity(curQuantity - quantity);

        var response = productVarianceMapper.entityToRequest(productVarianceRepository.save(productVariance));

        productService.checkStocksStillAvailable(productVariance.getProduct().getId());

        return response;
    }

    public void deleteVariancesByProductId(int productId) {
        List<ProductVariance> productVariances = productVarianceRepository.findByProductId(productId);
        productVarianceRepository.deleteAll(productVariances);
    }

    private void generateVariations(ProductDto product) {
        List<ProductVariance> result = new ArrayList<>();

        List<List<ProductAttributeAndValueDto>> combinations = generateCombinations(product.getAttributesAndAttributeValues());

        List<List<ProductAttributeAndValue>> combinationsEntities = new ArrayList<>();

        for (List<ProductAttributeAndValueDto> combination : combinations) {
            List<ProductAttributeAndValue> combinationEntities = new ArrayList<>();

            for (ProductAttributeAndValueDto attributeAndValue : combination) {
                ProductAttributeAndValue attributeAndValueEntity = productAttributeAndValueMapper.dtoToEntity(attributeAndValue);
                combinationEntities.add(productAttributeAndValueRepository.save(attributeAndValueEntity));
            }

            combinationsEntities.add(combinationEntities);
        }


        for (List<ProductAttributeAndValue> combination : combinationsEntities) {
            ProductVariance variance = new ProductVariance();
            variance.setProduct(productMapper.toEntity(product));
            variance.setAttributesAndValues(combination);
            variance.setQuantity(5);
            productVarianceRepository.save(variance);
            result.add(variance);
        }
    }

    private List<List<ProductAttributeAndValueDto>> generateCombinations(List<ProductAttributeAndAttributeValuesDto> attributesAndValues) {
        List<List<ProductAttributeAndValueDto>> result = new ArrayList<>();

        generateVariationsRecursive(result, new ArrayList<>(), 0, attributesAndValues);

        return result;
    }

    private void generateVariationsRecursive(List<List<ProductAttributeAndValueDto>> result,
                                             List<ProductAttributeAndValueDto> current,
                                             int depth,
                                             List<ProductAttributeAndAttributeValuesDto> attributesAndValues) {
        if (depth == attributesAndValues.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        ProductAttributeAndAttributeValuesDto attributeAndValues = attributesAndValues.get(depth);
        ProductAttributeDto attribute = attributeAndValues.getAttribute();
        List<ProductAttributeValueDto> values = attributeAndValues.getValues();

        for (ProductAttributeValueDto value : values) {
            ProductAttributeAndValueDto variation = new ProductAttributeAndValueDto();

            ProductAttributeDto attributeDto = new ProductAttributeDto();
            attributeDto.setId(attribute.getId());
            attributeDto.setName(attribute.getName());
            variation.setAttribute(attributeDto);

            ProductAttributeValueDto valueDto = new ProductAttributeValueDto();
            valueDto.setId(value.getId());
            valueDto.setValue(value.getValue());
            variation.setAttributeValue(valueDto);

            current.add(variation);
            generateVariationsRecursive(result, current, depth + 1, attributesAndValues);
            current.remove(current.size() - 1);
        }
    }
}
