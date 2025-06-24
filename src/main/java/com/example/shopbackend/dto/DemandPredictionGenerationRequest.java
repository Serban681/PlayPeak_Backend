package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DemandPredictionGenerationRequest {
    private int productVarianceId;
    private List<OrderDto> orders;
    private List<SimpleUserDto> users;
    private int daysToPredict;
    private int totalDays;
}
