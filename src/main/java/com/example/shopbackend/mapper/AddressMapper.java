package com.example.shopbackend.mapper;

import com.example.shopbackend.dto.AddressDto;
import com.example.shopbackend.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);
    Address toEntity(AddressDto addressDto);
}
