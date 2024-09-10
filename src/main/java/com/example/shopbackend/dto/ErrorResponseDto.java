package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorResponseDto {
    private String message;

    public ErrorResponseDto(String message) {
        this.message = message;
    }
}
