package com.example.shopbackend.repository;

import com.example.shopbackend.entity.ProductAttributeAndValue;
import org.springframework.data.repository.CrudRepository;

public interface ProductAttributeValueAndQuantityRepository extends CrudRepository<ProductAttributeAndValue, Integer> {
}
