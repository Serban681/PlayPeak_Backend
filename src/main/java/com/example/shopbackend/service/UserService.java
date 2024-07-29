package com.example.shopbackend.service;

import com.example.shopbackend.dto.AddressDto;
import com.example.shopbackend.dto.LoginRequestDto;
import com.example.shopbackend.dto.SimpleUserDto;
import com.example.shopbackend.dto.UserDto;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.exceptions.InvalidEmailOrPasswordException;
import com.example.shopbackend.mapper.UserMapper;
import com.example.shopbackend.repository.UserRepository;
import com.example.shopbackend.utils.PasswordEncoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressService addressService;

    public UserService(UserRepository userRepository, UserMapper userMapper, AddressService addressService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.addressService = addressService;
    }

    public UserDto login(LoginRequestDto loginRequest) {
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail());

            String encodedPassword = PasswordEncoder.encodePassword(loginRequest.getPassword());

            if(encodedPassword.equals(user.getPassword())) {
                return userMapper.toDto(user);
            } else {
                throw new InvalidEmailOrPasswordException();
            }
        } catch (Exception e) {
            throw new InvalidEmailOrPasswordException();
        }
    }

    public UserDto getOneById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));

        return userMapper.toDto(user);
    }

    public List<SimpleUserDto> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(userMapper::toSimpleDto).toList();
    }

    public UserDto create(UserDto userDto) throws NoSuchAlgorithmException {
        setUserDto(userDto);

        userDto.setPassword(PasswordEncoder.encodePassword(userDto.getPassword()));

        User user = userMapper.toEntity(userDto);

        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto update(UserDto userDto) {
        setUserDto(userDto);

        User user = userMapper.toEntity(userDto);

        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    private void setUserDto(UserDto userDto) {
        AddressDto defaultDeliveryAddress = addressService.create(userDto.getDefaultDeliveryAddress());
        AddressDto defaultBillingAddress = addressService.create(userDto.getDefaultBillingAddress());

        userDto.setDefaultDeliveryAddress(defaultDeliveryAddress);
        userDto.setDefaultBillingAddress(defaultBillingAddress);
    }
}
