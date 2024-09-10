package com.example.shopbackend.repository.ProductRelatedRepositories;

import com.example.shopbackend.entity.ProductAttributeAndAttributeValues;
import org.springframework.data.repository.CrudRepository;

public interface ProductAttributeAndAttributeValuesRepository extends CrudRepository<ProductAttributeAndAttributeValues, Integer> {
    ProductAttributeAndAttributeValues findProductAttributeAndAttributeValuesById(int id);
}
