package com.example.shopbackend.dto;

import com.example.shopbackend.entity.DemandPrediction;
import com.example.shopbackend.entity.ProductVariance;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
public class DemandPredictionDto {
    private int id;

    private LocalDate date;

    private float noOfOrders;

    @JsonProperty("isPredicted")
    private boolean isPredicted;
}
