package com.example.shopbackend.repository;

import com.example.shopbackend.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findById(int id);
    User findByEmail(String email);
}
