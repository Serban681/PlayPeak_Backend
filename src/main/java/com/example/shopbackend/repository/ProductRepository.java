package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {}
