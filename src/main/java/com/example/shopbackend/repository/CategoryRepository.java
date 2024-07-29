package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
