package com.example.shopbackend.repository;

import com.example.shopbackend.entity.CartEntry;
import org.springframework.data.repository.CrudRepository;

public interface CartEntryRepository extends CrudRepository<CartEntry, Integer> {
}
