package com.example.shopbackend.dto;

import com.example.shopbackend.entity.Gender;
import com.example.shopbackend.entity.Role;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
public class UserDto {
    private int id;

    private String firstName;

    private String lastName;

    private Role role;

    private String profileImageUrl;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private int age;

    private LocalDate registrationDate;

    private String password;

    private AddressDto defaultDeliveryAddress;

    private AddressDto defaultBillingAddress;
}
