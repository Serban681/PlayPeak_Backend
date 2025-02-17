package com.example.shopbackend.dto;

import com.example.shopbackend.entity.PaymentType;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class OrderRequest {
    private int userId;
    private CartDto cart;
    private PaymentType paymentType;
    private AddressDto deliveryAddress;
    private AddressDto billingAddress;
    private LocalDate orderDate;
}
