package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResetPasswordDto {
    private int userId;
    private String newPassword;
}
