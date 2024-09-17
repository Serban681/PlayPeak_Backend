package com.example.shopbackend.repository.ProductRelatedRepositories;

import com.example.shopbackend.entity.ProductVariance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductVarianceRepository extends CrudRepository<ProductVariance, Integer> {
    List<ProductVariance> findAllByOrderByIdAsc();
    ProductVariance findById(int id);
    List<ProductVariance> findByProductId(int productId);
    List<ProductVariance> findByProductIdOrderByIdAsc(int productId);
}
