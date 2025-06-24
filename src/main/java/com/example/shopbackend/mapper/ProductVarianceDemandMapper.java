package com.example.shopbackend.mapper;

import com.example.shopbackend.dto.ProductVarianceDemandDto;
import com.example.shopbackend.entity.ProductVarianceDemand;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductVarianceMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductVarianceDemandMapper {
    private final ProductVarianceMapper productVarianceMapper;
    private final DemandPredictionMapper demandPredictionMapper;
    public ProductVarianceDemandMapper(
            ProductVarianceMapper productVarianceMapper,
            DemandPredictionMapper demandPredictionMapper
    ) {
        this.productVarianceMapper = productVarianceMapper;
        this.demandPredictionMapper = demandPredictionMapper;
    }
    public ProductVarianceDemand dtoToEntity(ProductVarianceDemandDto productVarianceDemandDto) {
        ProductVarianceDemand productVarianceDemand = new ProductVarianceDemand();
        productVarianceDemand.setId(productVarianceDemandDto.getId());
        productVarianceDemand.setProductVariance(productVarianceMapper.dtoToEntity(productVarianceDemandDto.getProductVariance()));
        productVarianceDemand.setDemandPredictions(productVarianceDemandDto.getDemandPredictions().stream()
                .map(demandPredictionMapper::dtoToEntity)
                .collect(Collectors.toList())
        );
        return productVarianceDemand;

    }

    public ProductVarianceDemandDto entityToDto(ProductVarianceDemand productVarianceDemand) {
        ProductVarianceDemandDto productVarianceDemandDto = new ProductVarianceDemandDto();
        productVarianceDemandDto.setId(productVarianceDemand.getId());
        productVarianceDemandDto.setProductVariance(productVarianceMapper.entityToDto(productVarianceDemand.getProductVariance()));
        productVarianceDemandDto.setDemandPredictions(productVarianceDemand.getDemandPredictions().stream()
                .map(demandPredictionMapper::entityToDto)
                .collect(Collectors.toList())
        );
        return productVarianceDemandDto;
    }
}
