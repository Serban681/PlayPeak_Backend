package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
public class SimpleOrderRequest {
    private int productId;
    private int userId;
    private LocalDate date;
}
