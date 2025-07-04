package com.example.shopbackend.service;

import com.example.shopbackend.dto.*;
import com.example.shopbackend.entity.Address;
import com.example.shopbackend.entity.Gender;
import com.example.shopbackend.entity.Role;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.exceptions.InvalidEmailOrPasswordException;
import com.example.shopbackend.mapper.AddressMapper;
import com.example.shopbackend.mapper.UserMapper;
import com.example.shopbackend.repository.CartEntryRepository;
import com.example.shopbackend.repository.CartRepository;
import com.example.shopbackend.repository.UserRepository;
import com.example.shopbackend.utils.PasswordEncoder;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final EmailService emailService;
    private final CartService cartService;
    private final OrderService orderService;

    public UserService(UserRepository userRepository, UserMapper userMapper, AddressService addressService, AddressMapper addressMapper, EmailService emailService, CartService cartService, OrderService orderService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.emailService = emailService;
        this.cartService = cartService;
        this.orderService = orderService;
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

        emailService.sendEmail(email, "reset-pass@playpeak.com", "Reset Password",
                "<div style=\"font-family: Arial, sans-serif; color: #333;\">" +
                        "<h1 style=\"color: #F8B8ED;\">Reset Password</h1>" +
                        "<p>Dear User,</p>" +
                        "<p>To reset your password, click the link below:</p>" +
                        "<a href=\"http://localhost:3000/reset-password/" + user.getId() + "\">Reset password</a>" +
                        "<p>If you didn't request a password reset, ignore this email.</p>" +
                        "<p>Best regards,<br/>PlayPeak Team</p>" +
                        "</div>"
                );
    }

    public UserDto getOneById(int id) {
        User user = userRepository.findById(id);

        return userMapper.toDto(user);
    }

    public List<SimpleUserDto> getAllSimple() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(userMapper::toSimpleDto).toList();
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public UserDto create(UserDto userDto) throws NoSuchAlgorithmException {
        setUserDto(userDto);
        userDto.setRole(Role.USER);

        if(userDto.getRegistrationDate() == null) {
            userDto.setRegistrationDate(java.time.LocalDate.now());
        }

        userDto.setPassword(PasswordEncoder.encodePassword(userDto.getPassword()));

        User user = userMapper.toEntity(userDto);

        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> createMany(List<UserDto> userDtos) throws NoSuchAlgorithmException {
        List<UserDto> newUserDtos = new ArrayList<>();

        userDtos.forEach(userDto -> {
            try {
                newUserDtos.add(create(userDto));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });

        return newUserDtos;
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
        orderService.deleteByUserId(id);
        CartDto cartDto = cartService.removeCartFromUser(id);
        if(cartDto != null) {
            cartService.deleteCart(cartDto.getId());
        }
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        List<User> users = userRepository.findAll();

        users.forEach(user -> {
            delete(user.getId());
        });
    }

    public void initializeDefaultAdmin() throws NoSuchAlgorithmException {
        if(userRepository.countByRole(Role.ADMIN) == 0) {
            User user = new User();

            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setRole(Role.ADMIN);
            user.setProfileImageUrl("https://wallpapers-clan.com/wp-content/uploads/2022/05/meme-pfp-15.jpg");
            user.setEmail("admin@playpeak.com");
            user.setPhoneNumber("000000000");
            user.setGender(Gender.NOT_MENTIONED);
            user.setAge(20);
            user.setPassword(PasswordEncoder.encodePassword("admin"));
            user.setRegistrationDate(java.time.LocalDate.now());

            AddressDto address = new AddressDto();
            address.setStreetLine("Play Street");
            address.setPostalCode(123456);
            address.setCity("Miami");
            address.setCounty("Florida");
            address.setCountry("USA");

            user.setDefault_delivery_address(addressMapper.toEntity(addressService.create(address)));
            user.setDefault_billing_address(addressMapper.toEntity(addressService.create(address)));

            userRepository.save(user);
        }
    }

    private void setUserDto(UserDto userDto) {
        AddressDto defaultDeliveryAddress = addressService.create(userDto.getDefaultDeliveryAddress());
        AddressDto defaultBillingAddress = addressService.create(userDto.getDefaultBillingAddress());

        userDto.setDefaultDeliveryAddress(defaultDeliveryAddress);
        userDto.setDefaultBillingAddress(defaultBillingAddress);
    }
}
