package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CartEntryDto {
    private int id;
    // private ProductVarianceDto productVariance;
    private ProductDto product;
    private int quantity;
    private float pricePerPiece;
    private float totalPrice;
}
