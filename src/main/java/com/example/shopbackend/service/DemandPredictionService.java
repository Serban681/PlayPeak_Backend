package com.example.shopbackend.service;

import com.example.shopbackend.dto.DemandPredictionDto;
import com.example.shopbackend.dto.DemandPredictionGenerationRequest;
import com.example.shopbackend.dto.DemandPredictionGenerationResponse;
import com.example.shopbackend.dto.ProductVarianceDemandDto;
import com.example.shopbackend.entity.DemandPrediction;
import com.example.shopbackend.entity.Order;
import com.example.shopbackend.entity.ProductVarianceDemand;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.mapper.DemandPredictionMapper;
import com.example.shopbackend.mapper.OrderRelatedMappers.OrderMapper;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductVarianceMapper;
import com.example.shopbackend.mapper.ProductVarianceDemandMapper;
import com.example.shopbackend.mapper.UserMapper;
import com.example.shopbackend.repository.DemandPredictionRepository;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.ProductVarianceDemandRepository;
import com.example.shopbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandPredictionService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${spring.demand.service.url}")
    private String demandServiceUrl;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final Builder webClientBuilder;
    private final DemandPredictionRepository demandPredictionRepository;
    private final ProductVarianceDemandRepository productVarianceDemandRepository;
    private final ProductVarianceService productVarianceService;
    private final ProductVarianceMapper productVarianceMapper;
    private  final DemandPredictionMapper demandPredictionMapper;
    private final ProductVarianceDemandMapper productVarianceDemandMapper;

    public DemandPredictionService(OrderRepository orderRepository,
                                   UserRepository userRepository,
                                   OrderMapper orderMapper,
                                   UserMapper userMapper,
                                   Builder webClientBuilder,
                                   DemandPredictionRepository demandPredictionRepository,
                                   ProductVarianceDemandRepository productVarianceDemandRepository,
                                   ProductVarianceService productVarianceService,
                                   ProductVarianceMapper productVarianceMapper,
                                   DemandPredictionMapper demandPredictionMapper,
                                      ProductVarianceDemandMapper productVarianceDemandMapper
                                   ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.webClientBuilder = webClientBuilder;
        this.demandPredictionRepository = demandPredictionRepository;
        this.productVarianceDemandRepository = productVarianceDemandRepository;
        this.productVarianceService = productVarianceService;
        this.productVarianceMapper = productVarianceMapper;
        this.demandPredictionMapper = demandPredictionMapper;
        this.productVarianceDemandMapper = productVarianceDemandMapper;
    }

    public List<ProductVarianceDemandDto> getDemandPredictionsForProduct(int productId) {
        List<ProductVarianceDemand> productVarianceDemands = productVarianceDemandRepository.findByProductId(productId);

        if (productVarianceDemands != null && !productVarianceDemands.isEmpty()) {
            return productVarianceDemands.stream()
                    .map(productVarianceDemandMapper::entityToDto)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    public ProductVarianceDemandDto generateNewDemandPrediction(int productVarianceId, int daysToPredict, int totalDays) {
        List<Order> orders = orderRepository.findAllByProductVarianceId(productVarianceId);
        List<User> users = userRepository.findAll();

        DemandPredictionGenerationRequest request = new DemandPredictionGenerationRequest();
        request.setProductVarianceId(productVarianceId);
        request.setOrders(orders.stream().map(orderMapper::entityToDto).toList());
        request.setUsers(users.stream().map(userMapper::toSimpleDto).toList());
        request.setDaysToPredict(daysToPredict);
        request.setTotalDays(totalDays);

        DemandPredictionGenerationResponse response = webClientBuilder.build()
                .post()
                .uri(demandServiceUrl + "/generate-demand-prediction")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DemandPredictionGenerationResponse.class)
                .block();

        ProductVarianceDemand productVarianceDemand = productVarianceDemandRepository.findByProductVarianceId(productVarianceId);

        if (productVarianceDemand == null) {
            productVarianceDemand = new ProductVarianceDemand();
            productVarianceDemand.setProductVariance(productVarianceMapper.requestToEntity(productVarianceService.getById(productVarianceId)));
            assert response != null;
        } else {
            List<DemandPrediction> existingPredictions = productVarianceDemand.getDemandPredictions();
            productVarianceDemand.setDemandPredictions(Collections.emptyList());
            existingPredictions.forEach(demandPrediction -> demandPredictionRepository.deleteById(demandPrediction.getId()));
        }

        List<DemandPrediction> demandPredictions = response.getDemandPredictions().stream()
                .map(demandPredictionMapper::dtoToEntity)
                .peek(demandPrediction -> demandPredictionRepository.save(demandPrediction))
                .collect(Collectors.toCollection(ArrayList::new));

        productVarianceDemand.setDemandPredictions(demandPredictions);

        return productVarianceDemandMapper.entityToDto(productVarianceDemandRepository.save(productVarianceDemand));
    }
}
