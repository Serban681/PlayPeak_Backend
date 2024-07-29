package com.example.shopbackend.controller;

import com.example.shopbackend.dto.AddressDto;
import com.example.shopbackend.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAll() throws Exception {
        return new ResponseEntity<>(addressService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public AddressDto getOneById(@PathVariable  int id) {
        return addressService.getOneById(id);
    }

    @PostMapping
    public ResponseEntity<AddressDto> create(@RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.create(addressDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AddressDto> update(@RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.update(addressDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        addressService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
