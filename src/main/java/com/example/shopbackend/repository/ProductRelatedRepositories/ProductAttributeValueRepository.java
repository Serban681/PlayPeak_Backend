package com.example.shopbackend.repository.ProductRelatedRepositories;

import com.example.shopbackend.entity.ProductAttributeValue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductAttributeValueRepository extends CrudRepository<ProductAttributeValue, Integer> {
    ProductAttributeValue findProductAttributeValueByValue(String value);

    @Query("SELECT pav FROM ProductAttributeValue pav WHERE pav NOT IN (SELECT paav.attributeValues FROM ProductAttributeAndAttributeValues paav)")
    List<ProductAttributeValue> findUnreferencedProductAttributeValues();
}
