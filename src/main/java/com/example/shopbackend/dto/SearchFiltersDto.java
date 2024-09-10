package com.example.shopbackend.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SearchFiltersDto {
    public String[] categories;
    public String sortMethod;
}
