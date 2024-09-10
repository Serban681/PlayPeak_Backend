package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.ProductVarianceDto;
import com.example.shopbackend.entity.ProductVariance;
import org.springframework.stereotype.Service;

@Service
public class ProductVarianceMapper {
        private final ProductMapper productMapper;
//        private final ProductAttributeAndValueMapper productAttributeAndValueMapper;

        public ProductVarianceMapper(ProductMapper productMapper) {
            this.productMapper = productMapper;
        }

        public ProductVarianceDto toDto(ProductVariance productVariance) {
            ProductVarianceDto productVarianceDto = new ProductVarianceDto();
            productVarianceDto.setId(productVariance.getId());
            productVarianceDto.setProduct(productMapper.toDto(productVariance.getProduct()));
            //productVarianceDto.setAttributesAndValues(productVariance.getAttributesAndValues());
            return null;
        }
}
