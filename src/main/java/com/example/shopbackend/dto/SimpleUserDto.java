package com.example.shopbackend.dto;

import com.example.shopbackend.entity.Role;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
public class SimpleUserDto {
    private int id;

    private String firstName;

    private String lastName;

    private Role role;

    private String email;

    private int age;

    private String gender;

    private String phoneNumber;

    private LocalDate registrationDate;
}
