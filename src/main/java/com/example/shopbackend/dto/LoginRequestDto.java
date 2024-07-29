package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
