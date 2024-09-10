package com.example.shopbackend.repository;

import com.example.shopbackend.entity.CartEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartEntryRepository extends CrudRepository<CartEntry, Integer> {
    List<CartEntry> findAll();
}
