package com.example.shopbackend.controller;

import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.dto.OrderRequest;
import com.example.shopbackend.entity.Order;
import com.example.shopbackend.service.OrderService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderRequest orderRequest) throws MessagingException {
        return new ResponseEntity<>(orderService.create(orderRequest), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        orderService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
