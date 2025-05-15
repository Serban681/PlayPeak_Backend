package com.example.shopbackend.exceptions;

public class EmptyStockException extends RuntimeException{
    public EmptyStockException() {
        super("Sorry, this item is currently out of stock and can't be added to your cart.");
    }
}
