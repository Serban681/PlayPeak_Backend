package com.example.shopbackend.repository.ProductRelatedRepositories;


import com.example.shopbackend.entity.ProductAttributeAndValue;
import org.springframework.data.repository.CrudRepository;

public interface ProductAttributeAndValueRepository extends CrudRepository<ProductAttributeAndValue, Integer> {
    //ProductAttributeAndValue findProductAttributeAndValueById(int id);
}
