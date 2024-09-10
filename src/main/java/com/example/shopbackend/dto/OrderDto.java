package com.example.shopbackend.dto;

import com.example.shopbackend.entity.PaymentType;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
public class OrderDto {
    private int id;
    private UserDto user;
    private CartDto cart;
    private PaymentType paymentType;
    private AddressDto deliveryAddress;
    private AddressDto billingAddress;
    private float totalPrice;
    private LocalDate orderDate;
}
