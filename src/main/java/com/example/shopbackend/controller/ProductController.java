package com.example.shopbackend.controller;

import com.example.shopbackend.dto.CategorizedProductsResponseDto;
import com.example.shopbackend.dto.ProductRequest;
import com.example.shopbackend.dto.SearchFiltersDto;
import com.example.shopbackend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<CategorizedProductsResponseDto>> getAll(@RequestBody SearchFiltersDto searchFiltersDto) {
        return new ResponseEntity<>(productService.getAll(searchFiltersDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRequest> getOneById(@PathVariable int id) {
        return new ResponseEntity<>(productService.getOneById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductRequest> create(@RequestBody ProductRequest product) {
        return new ResponseEntity<>(productService.create(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
