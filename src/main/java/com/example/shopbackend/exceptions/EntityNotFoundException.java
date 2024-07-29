package com.example.shopbackend.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityName, int id) {
        super(entityName + " with id " + id + " not found");
    }
}
