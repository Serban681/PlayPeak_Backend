package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SimpleUserDto {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
