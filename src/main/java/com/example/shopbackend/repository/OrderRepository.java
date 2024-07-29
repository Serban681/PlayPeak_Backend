package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
