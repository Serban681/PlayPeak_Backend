package com.example.shopbackend.repository;

import com.example.shopbackend.entity.ProductAttribute;
import org.springframework.data.repository.CrudRepository;

public interface ProductAttributeRepository extends CrudRepository<ProductAttribute, Integer> {
}
