package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DemandPredictionGenerationResponse {
    private int productVarianceId;
    private List<DemandPredictionDto> demandPredictions;
}
