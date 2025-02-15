package com.example.shopbackend.dto;

import com.example.shopbackend.entity.Gender;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserDto {
    private int id;

    private String firstName;

    private String lastName;

    private String profileImageUrl;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private int age;

    private String password;

    private AddressDto defaultDeliveryAddress;

    private AddressDto defaultBillingAddress;
}
