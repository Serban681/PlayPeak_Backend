package com.example.shopbackend.controller;

import com.example.shopbackend.dto.LoginRequestDto;
import com.example.shopbackend.dto.ResetPasswordDto;
import com.example.shopbackend.dto.SimpleUserDto;
import com.example.shopbackend.dto.UserDto;
import com.example.shopbackend.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/reset-pass-email")
    public ResponseEntity<Void> sendResetPassEmail(@RequestParam String email) throws MessagingException {
        userService.sendResetPasswordEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws NoSuchAlgorithmException {
        userService.updatePassword(resetPasswordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/simple")
    public List<SimpleUserDto> getAllSimple() {
        return userService.getAllSimple();
    }

    @GetMapping("/{id}")
    public UserDto getOneById(@PathVariable int id) {
        return userService.getOneById(id);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) throws NoSuchAlgorithmException {
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/create-many")
    public ResponseEntity<List<UserDto>> createMany(@RequestBody List<UserDto> userDtos) throws NoSuchAlgorithmException {
        return new ResponseEntity<>(userService.createMany(userDtos), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.update(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAll() {
        userService.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
