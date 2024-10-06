package com.example.shopbackend.dto;

import com.example.shopbackend.entity.PaymentType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderRequest {
    private int userId;
    private CartDto cart;
    private PaymentType paymentType;
    private AddressDto deliveryAddress;
    private AddressDto billingAddress;
}
