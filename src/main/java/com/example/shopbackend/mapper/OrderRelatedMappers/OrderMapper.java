package com.example.shopbackend.mapper.OrderRelatedMappers;

import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.entity.Order;
import com.example.shopbackend.mapper.AddressMapper;
import com.example.shopbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    UserMapper userMapper;
    CartMapper cartMapper;
    AddressMapper addressMapper;

    public OrderMapper(
            UserMapper userMapper,
            CartMapper cartMapper,
            AddressMapper addressMapper
    ) {
        this.userMapper = userMapper;
        this.cartMapper = cartMapper;
        this.addressMapper = addressMapper;
    }

    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUser(userMapper.toDto(order.getUser()));
        orderDto.setCart(cartMapper.toDto(order.getCart()));
        orderDto.setPaymentType(order.getPaymentType());
        orderDto.setDeliveryAddress(addressMapper.toDto(order.getDeliveryAddress()));
        orderDto.setBillingAddress(addressMapper.toDto(order.getBillingAddress()));
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderDate(order.getOrderDate());

        return orderDto;
    }

    public Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setUser(userMapper.toEntity(orderDto.getUser()));
        order.setCart(cartMapper.toEntity(orderDto.getCart()));
        order.setPaymentType(orderDto.getPaymentType());
        order.setDeliveryAddress(addressMapper.toEntity(orderDto.getDeliveryAddress()));
        order.setBillingAddress(addressMapper.toEntity(orderDto.getBillingAddress()));
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setOrderDate(orderDto.getOrderDate());

        return order;
    }
}
