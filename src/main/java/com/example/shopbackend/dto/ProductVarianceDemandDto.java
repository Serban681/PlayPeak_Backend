package com.example.shopbackend.dto;

import com.example.shopbackend.entity.DemandPrediction;
import com.example.shopbackend.entity.ProductVariance;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductVarianceDemandDto {
    private int id;

    private ProductVarianceDto productVariance;

    private List<DemandPredictionDto> demandPredictions;
}
