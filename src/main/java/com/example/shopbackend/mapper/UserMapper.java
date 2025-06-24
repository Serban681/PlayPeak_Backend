package com.example.shopbackend.mapper;

import com.example.shopbackend.dto.SimpleUserDto;
import com.example.shopbackend.dto.UserDto;
import com.example.shopbackend.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    AddressMapper addressMapper;

    public UserMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole());
        userDto.setEmail(user.getEmail());
        userDto.setProfileImageUrl(user.getProfileImageUrl());
        userDto.setAge(user.getAge());
        userDto.setGender(user.getGender());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRegistrationDate(user.getRegistrationDate());
        userDto.setPassword(user.getPassword());
        userDto.setDefaultDeliveryAddress(addressMapper.toDto(user.getDefault_delivery_address()));
        userDto.setDefaultBillingAddress(addressMapper.toDto(user.getDefault_billing_address()));

        return userDto;
    }
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setProfileImageUrl(userDto.getProfileImageUrl());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRegistrationDate(userDto.getRegistrationDate());
        user.setPassword(userDto.getPassword());
        user.setDefault_billing_address(addressMapper.toEntity(userDto.getDefaultBillingAddress()));
        user.setDefault_delivery_address(addressMapper.toEntity(userDto.getDefaultDeliveryAddress()));

        return user;
    }
    public SimpleUserDto toSimpleDto(User user) {
        SimpleUserDto simpleUserDto = new SimpleUserDto();
        simpleUserDto.setId(user.getId());
        simpleUserDto.setFirstName(user.getFirstName());
        simpleUserDto.setLastName(user.getLastName());
        simpleUserDto.setRole(user.getRole());
        simpleUserDto.setEmail(user.getEmail());
        simpleUserDto.setAge(user.getAge());
        simpleUserDto.setGender(user.getGender().name());
        simpleUserDto.setPhoneNumber(user.getPhoneNumber());
        simpleUserDto.setRegistrationDate(user.getRegistrationDate());

        return simpleUserDto;
    }
}
