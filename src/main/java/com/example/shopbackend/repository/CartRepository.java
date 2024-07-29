package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {}
