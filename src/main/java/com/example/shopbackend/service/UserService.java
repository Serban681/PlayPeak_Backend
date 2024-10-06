package com.example.shopbackend.service;

import com.example.shopbackend.dto.*;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.exceptions.InvalidEmailOrPasswordException;
import com.example.shopbackend.mapper.AddressMapper;
import com.example.shopbackend.mapper.UserMapper;
import com.example.shopbackend.repository.UserRepository;
import com.example.shopbackend.utils.PasswordEncoder;
import jakarta.mail.MessagingException;
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
    private final AddressMapper addressMapper;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, UserMapper userMapper, AddressService addressService, AddressMapper addressMapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.emailService = emailService;
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

    public void updatePassword(ResetPasswordDto resetPasswordDto) throws NoSuchAlgorithmException {
        User user = userRepository.findById(resetPasswordDto.getUserId());

        user.setPassword(PasswordEncoder.encodePassword(resetPasswordDto.getNewPassword()));

        userRepository.save(user);
    }

    public void sendResetPasswordEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);

        emailService.sendEmail(email, "reset-pass@cool-shop.com", "Reset Password",
                "<div style=\"font-family: Arial, sans-serif; color: #333;\">" +
                        "<h1 style=\"color: #F8B8ED;\">Reset Password</h1>" +
                        "<p>Dear User,</p>" +
                        "<p>To reset your password, click the link below:</p>" +
                        "<a href=\"http://localhost:3000/reset-password/" + user.getId() + "\">Reset password</a>" +
                        "<p>If you didn't request a password reset, ignore this email.</p>" +
                        "<p>Best regards,<br/>Cool Shop Team</p>" +
                        "</div>"
                );
    }

    public UserDto getOneById(int id) {
        User user = userRepository.findById(id);

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
        AddressDto defaultDeliveryAddress = userDto.getDefaultDeliveryAddress();
        defaultDeliveryAddress.setId(0);

        AddressDto defaultBillingAddress = userDto.getDefaultBillingAddress();
        defaultBillingAddress.setId(0);

        User user = userMapper.toEntity(userDto);

        user.setDefault_delivery_address(addressMapper.toEntity(addressService.create(defaultDeliveryAddress)));
        user.setDefault_billing_address(addressMapper.toEntity(addressService.create(defaultBillingAddress)));

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
