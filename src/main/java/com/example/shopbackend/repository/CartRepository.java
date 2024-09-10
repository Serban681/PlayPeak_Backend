package com.example.shopbackend.repository;

import com.example.shopbackend.entity.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    List<Cart> findAll();
    Cart findCartByUserId(int userId);

    Cart findCartById(int id);

    @Query("SELECT c FROM Cart c JOIN c.cartEntries ce WHERE ce.id = ?1")
    Cart findCartByCartEntryId(int id);
}
