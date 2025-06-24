package com.example.shopbackend.repository;

import com.example.shopbackend.entity.ProductVarianceDemand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVarianceDemandRepository extends CrudRepository<ProductVarianceDemand, Integer> {
    ProductVarianceDemand findByProductVarianceId(int productVarianceId);
    @Query("""
        SELECT pvd
        FROM ProductVarianceDemand pvd
        JOIN pvd.productVariance pv
        JOIN pv.product p
        WHERE p.id = :productId
    """)
    List<ProductVarianceDemand> findByProductId(@Param("productId") int productId);
}
