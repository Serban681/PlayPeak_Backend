package com.example.shopbackend.repository;

import com.example.shopbackend.entity.DemandPrediction;
import com.example.shopbackend.entity.ProductVarianceDemand;
import org.springframework.data.repository.CrudRepository;

public interface DemandPredictionRepository extends CrudRepository<DemandPrediction, Integer> {
}
