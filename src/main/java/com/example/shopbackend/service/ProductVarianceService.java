package com.example.shopbackend.service;

import com.example.shopbackend.dto.*;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.entity.ProductAttributeAndAttributeValues;
import com.example.shopbackend.entity.ProductAttributeValue;
import com.example.shopbackend.entity.ProductVariance;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductVarianceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductVarianceService {
    private ProductVarianceRepository productVarianceRepository;

    public ProductVarianceService(
            ProductVarianceRepository productVarianceRepository
    ) {
        this.productVarianceRepository = productVarianceRepository;
    }

    public void create(ProductDto productDto, List<ProductAttributeAndAttributeValuesDto> paavDtos) {
        List<ProductVarianceDto> productVariances = generateVariations(productDto, paavDtos);
    }

    public List<ProductVarianceDto> generateVariations(ProductDto product, List<ProductAttributeAndAttributeValuesDto> attributesAndValues) {
        List<ProductVarianceDto> result = new ArrayList<>();

        List<List<ProductAttributeAndValueDto>> combinations = generateCombinations(attributesAndValues);

        for (List<ProductAttributeAndValueDto> combination : combinations) {
            ProductVarianceDto variance = new ProductVarianceDto();
            variance.setProduct(product);
            variance.setAttributesAndValues(combination);
            variance.setQuantity(0);

            result.add(variance);
        }

        return result;
    }

    public List<List<ProductAttributeAndValueDto>> generateCombinations(List<ProductAttributeAndAttributeValuesDto> attributesAndValues) {
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
            variation.setId(value.getId());
            variation.setName(attribute.getName());
            variation.setValue(value.getValue());

            current.add(variation);
            generateVariationsRecursive(result, current, depth + 1, attributesAndValues);
            current.remove(current.size() - 1);
        }
    }
}
