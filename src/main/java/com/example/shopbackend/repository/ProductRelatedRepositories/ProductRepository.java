package com.example.shopbackend.repository.ProductRelatedRepositories;

import com.example.shopbackend.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.StreamSupport;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();
    List<Product> findProductsByName(String name);
    List<Product> findAllByOrderByPriceAsc();
    Product findProductById(int id);

    List<Product> findProductsByNameContainingIgnoreCase(String name);

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findAllByOrderByNameAsc();

    List<Product> findAllByOrderByNameDesc();

    List<Product> findAllByOrderByAddedDate();

    List<Product> findAllByOrderByAddedDateDesc();
}
