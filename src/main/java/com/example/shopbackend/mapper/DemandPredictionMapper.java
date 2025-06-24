package com.example.shopbackend.mapper;

import com.example.shopbackend.dto.DemandPredictionDto;
import com.example.shopbackend.entity.DemandPrediction;
import org.springframework.stereotype.Service;

@Service
public class DemandPredictionMapper {
    public DemandPrediction dtoToEntity(DemandPredictionDto demandPredictionDto) {
        DemandPrediction demandPrediction = new DemandPrediction();
        demandPrediction.setId(demandPredictionDto.getId());
        demandPrediction.setDate(demandPredictionDto.getDate());
        demandPrediction.setNoOfOrders(demandPredictionDto.getNoOfOrders());
        demandPrediction.setPredicted(demandPredictionDto.isPredicted());
        return demandPrediction;
    }

    public DemandPredictionDto entityToDto(DemandPrediction demandPrediction) {
        DemandPredictionDto demandPredictionDto = new DemandPredictionDto();
        demandPredictionDto.setId(demandPrediction.getId());
        demandPredictionDto.setDate(demandPrediction.getDate());
        demandPredictionDto.setNoOfOrders(demandPrediction.getNoOfOrders());
        demandPredictionDto.setPredicted(demandPrediction.isPredicted());
        return demandPredictionDto;
    }
}
