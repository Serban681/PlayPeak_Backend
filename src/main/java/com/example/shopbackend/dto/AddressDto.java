package com.example.shopbackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@ToString
@Data
public class AddressDto {
    private int id;

    private String streetLine;

    private int postalCode;

    private String city;

    private String county;

    private String country;
}
