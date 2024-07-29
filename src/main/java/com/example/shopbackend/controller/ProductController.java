package com.example.shopbackend.controller;

import com.example.shopbackend.dto.ProductDto;
import com.example.shopbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping
//    public List<ProductDto> getAll() {
//        return productService.getAll();
//    }
//    @RequestMapping("{id}")
//    public ProductDto geOneById(@PathVariable int id) {
//        return productService.getOneById(id);
//    }
}
