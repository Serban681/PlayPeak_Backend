package com.example.shopbackend.repository.ProductRelatedRepositories;

import com.example.shopbackend.entity.ProductAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductAttributeRepository extends CrudRepository<ProductAttribute, Integer> {
    ProductAttribute findProductAttributeByName(String name);

    @Query("SELECT pa FROM ProductAttribute pa WHERE pa.id NOT IN (SELECT paav.attribute.id FROM ProductAttributeAndAttributeValues paav WHERE paav.attribute.id IS NOT NULL)")
    List<ProductAttribute> findUnreferencedProductAttributes();
}
