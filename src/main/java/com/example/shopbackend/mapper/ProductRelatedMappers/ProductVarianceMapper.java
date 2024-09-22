package com.example.shopbackend.mapper.ProductRelatedMappers;

import com.example.shopbackend.dto.ProductVarianceDto;
import com.example.shopbackend.dto.ProductVarianceRequest;
import com.example.shopbackend.entity.ProductVariance;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductRepository;
import com.example.shopbackend.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductVarianceMapper {
        private final ProductMapper productMapper;
        private final ProductAttributeAndValueMapper attributeAndValueMapper;
        private final ProductRepository productRepository;

        public ProductVarianceMapper(ProductMapper productMapper, ProductAttributeAndValueMapper attributeAndValueMapper, ProductRepository productRepository) {
            this.productMapper = productMapper;
            this.attributeAndValueMapper = attributeAndValueMapper;
            this.productRepository = productRepository;
        }

        public ProductVarianceDto entityToDto(ProductVariance productVariance) {
            ProductVarianceDto productVarianceDto = new ProductVarianceDto();
            productVarianceDto.setId(productVariance.getId());
            productVarianceDto.setProduct(productMapper.toDto(productVariance.getProduct()));
            productVarianceDto.setAttributesAndValues(
                    productVariance.getAttributesAndValues().stream().map(attributeAndValueMapper::entityToDto).toList()
            );
            productVarianceDto.setQuantity(productVariance.getQuantity());
            return productVarianceDto;
        }

        public ProductVariance dtoToEntity(ProductVarianceDto productVarianceDto) {
            ProductVariance productVariance = new ProductVariance();
            productVariance.setId(productVarianceDto.getId());
            productVariance.setProduct(productMapper.toEntity(productVarianceDto.getProduct()));
            productVariance.setAttributesAndValues(
                    productVarianceDto.getAttributesAndValues().stream().map(attributeAndValueMapper::dtoToEntity).toList()
            );
            productVariance.setQuantity(productVarianceDto.getQuantity());
            return productVariance;
        }

        public ProductVarianceRequest entityToRequest(ProductVariance productVariance) {
            ProductVarianceRequest productVarianceRequest = new ProductVarianceRequest();
            productVarianceRequest.setId(productVariance.getId());
            productVarianceRequest.setProduct(productMapper.dtoToRequest(productMapper.toDto(productVariance.getProduct())));
            productVarianceRequest.setAttributesAndValues(
                    productVariance.getAttributesAndValues().stream().map(attributeAndValueMapper::entityToRequest).toList()
            );
            productVarianceRequest.setQuantity(productVariance.getQuantity());

            return productVarianceRequest;
        }

        public ProductVariance requestToEntity(ProductVarianceRequest productVarianceRequest) {
            ProductVariance productVariance = new ProductVariance();
            productVariance.setId(productVarianceRequest.getId());
            productVariance.setProduct(productMapper.toEntity(productMapper.requestToDto(productVarianceRequest.getProduct())));
            productVariance.setAttributesAndValues(
                    productVarianceRequest.getAttributesAndValues().stream().map(attributeAndValueMapper::requestToEntity).toList()
            );
            productVariance.setQuantity(productVarianceRequest.getQuantity());

            return productVariance;
        }
}
