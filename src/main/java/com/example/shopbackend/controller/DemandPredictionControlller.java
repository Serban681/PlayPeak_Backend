package com.example.shopbackend.controller;

import com.example.shopbackend.dto.CategoryDto;
import com.example.shopbackend.dto.DemandPredictionGenerationRequest;
import com.example.shopbackend.dto.DemandPredictionGenerationResponse;
import com.example.shopbackend.dto.ProductVarianceDemandDto;
import com.example.shopbackend.service.CategoryService;
import com.example.shopbackend.service.DemandPredictionService;
import org.hibernate.query.QueryParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/demand-prediction")
public class DemandPredictionControlller {
    private final DemandPredictionService demandPredictionService;

    public DemandPredictionControlller(DemandPredictionService demandPredictionService) {
        this.demandPredictionService = demandPredictionService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductVarianceDemandDto>> getProductVarianceDemands(@PathVariable int productId) {
        return new ResponseEntity<>(demandPredictionService.getDemandPredictionsForProduct(productId), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductVarianceDemandDto> generateDemandPrediction(@PathVariable int id, @RequestParam int daysToPredict, @RequestParam int totalDays) {
        return new ResponseEntity<>(demandPredictionService.generateNewDemandPrediction(id, daysToPredict, totalDays), HttpStatus.OK);
    }
}
