package com.example.shopbackend.service;

import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.mapper.OrderRelatedMappers.OrderMapper;
import com.example.shopbackend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
    }

    public OrderDto create(OrderDto orderDto) {
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }
}
