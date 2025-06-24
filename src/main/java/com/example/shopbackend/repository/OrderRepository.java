package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAll();

    Order findById(int id);

    @Query("""
    SELECT DISTINCT o
    FROM Order o
    JOIN o.cart c
    JOIN c.cartEntries ce
    WHERE ce.productVariance.id = :productVarianceId
    """)
    List<Order> findAllByProductVarianceId(int productVarianceId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findAllByUserId(int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Order o WHERE o.user.id = :userId")
    void deleteAllByUserId(int userId);
}
