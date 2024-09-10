package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAll();
}
