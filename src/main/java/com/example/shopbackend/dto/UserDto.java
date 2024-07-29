package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserDto {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private AddressDto defaultDeliveryAddress;

    private AddressDto defaultBillingAddress;
}
