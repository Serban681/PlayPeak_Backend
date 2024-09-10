package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class CartDto {
    private int id;
    private UserDto user;
    private List<CartEntryDto> cartEntries;
    private float totalPrice;
}
