package com.example.shopbackend.controller;

import com.example.shopbackend.dto.CartDto;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getAll() {
        return ResponseEntity.ok(cartService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/add-product")
    public ResponseEntity<CartDto> addProductToCart(@RequestParam int productVarianceId, @RequestParam int cartId) {
        return ResponseEntity.ok(cartService.addProductToCart(productVarianceId, cartId));
    }

    @PutMapping("/product/update-quantity")
    public ResponseEntity<CartDto> updateProductQuantity(@RequestParam int cartEntryId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.modifyCartEntryQuantity(cartEntryId, quantity));
    }

    @DeleteMapping("/remove-product")
    public ResponseEntity<CartDto> removeProductFromCart(@RequestParam int cartEntryId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(cartEntryId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable  int id) {
        cartService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
