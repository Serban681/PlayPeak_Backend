package com.example.shopbackend.controller;

import com.example.shopbackend.dto.LoginRequestDto;
import com.example.shopbackend.dto.ProductAttributeAndAttributeValuesDto;
import com.example.shopbackend.dto.SimpleUserDto;
import com.example.shopbackend.dto.UserDto;
import com.example.shopbackend.service.ProductAttributesAndAttributeValuesService;
import com.example.shopbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/paav")
public class ProductAttributesAndAttributeValuesController {
    private final ProductAttributesAndAttributeValuesService paavService;
    public ProductAttributesAndAttributeValuesController(ProductAttributesAndAttributeValuesService paavService) {
        this.paavService = paavService;
    }

    @GetMapping
    public List<ProductAttributeAndAttributeValuesDto> getAll() {
        return paavService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductAttributeAndAttributeValuesDto> getOneById(@PathVariable int id) {
        return new ResponseEntity<>(paavService.getOneById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        paavService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
