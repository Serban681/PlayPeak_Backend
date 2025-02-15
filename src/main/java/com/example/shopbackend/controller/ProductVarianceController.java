package com.example.shopbackend.controller;

import com.example.shopbackend.dto.ProductVarianceRequest;
import com.example.shopbackend.service.ProductVarianceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/product-variance")
public class ProductVarianceController {
    private final ProductVarianceService productVarianceService;

    public ProductVarianceController(ProductVarianceService productVarianceService) {
        this.productVarianceService = productVarianceService;
    }

    @GetMapping
    public ResponseEntity<List<ProductVarianceRequest>> getAll() {
        return new ResponseEntity<>(productVarianceService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ProductVarianceRequest>> getByProductId(@PathVariable int id) {
        return new ResponseEntity<>(productVarianceService.getByProductId(id), HttpStatus.OK);
    }

    @PutMapping("/update-quantity/{id}")
    public ResponseEntity<ProductVarianceRequest> updateQuantity(@PathVariable int id, @RequestParam int quantity) {
        return new ResponseEntity<>(productVarianceService.updateQuantity(id, quantity), HttpStatus.OK);
    }
}
