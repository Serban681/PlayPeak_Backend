package com.example.shopbackend.controller;

import com.example.shopbackend.dto.CartEntryDto;
import com.example.shopbackend.entity.CartEntry;
import com.example.shopbackend.service.CartEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/cart-entry")
public class CartEntryController {
    private final CartEntryService cartEntryService;

    public CartEntryController(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @GetMapping
    public ResponseEntity<List<CartEntryDto>> getAll() {
        return ResponseEntity.ok(cartEntryService.getAll());
    }

    @PostMapping
    public ResponseEntity<CartEntryDto> create(@RequestParam int productId, @RequestParam int cartId) {
        return ResponseEntity.ok(cartEntryService.create(productId, cartId));
    }

    @PostMapping("/update-quantity")
    public ResponseEntity<CartEntryDto> updateQuantity(@RequestParam int cartEntryId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartEntryService.updateQuantity(cartEntryId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCartAndDelete(@RequestParam int cartEntryId, @RequestParam int cartId) {
        cartEntryService.removeCartEntry(cartEntryId, cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        cartEntryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
