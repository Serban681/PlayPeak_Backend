package com.example.shopbackend.repository.ProductRelatedRepositories;

import com.example.shopbackend.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findCategoriesByName(String name);

    @Query("SELECT c FROM Category c WHERE c.id NOT IN (SELECT p.category.id FROM Product p WHERE p.category IS NOT NULL)")
    List<Category> findUnreferencedCategories();

    Category findCategoryByName(String name);
}
