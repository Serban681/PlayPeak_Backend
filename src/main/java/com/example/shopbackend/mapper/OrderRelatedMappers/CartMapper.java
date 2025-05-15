package com.example.shopbackend.mapper.OrderRelatedMappers;

import com.example.shopbackend.dto.CartDto;
import com.example.shopbackend.entity.Cart;
import com.example.shopbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class CartMapper {
    private CartEntryMapper cartEntryMapper;
    private UserMapper userMapper;

    public CartMapper(CartEntryMapper cartEntryMapper, UserMapper userMapper) {
        this.cartEntryMapper = cartEntryMapper;
        this.userMapper = userMapper;
    }

    public CartDto entityToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        if(cart.getUser() != null)
            cartDto.setUser(userMapper.toDto(cart.getUser()));
        cartDto.setCartEntries(cart.getCartEntries().stream().map(cartEntryMapper::toDto).toList());
        cartDto.setTotalPrice(cart.getTotalPrice());

        return cartDto;
    }

    public Cart dtoToEntity(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        if(cartDto.getUser() != null)
            cart.setUser(userMapper.toEntity(cartDto.getUser()));
        cart.setCartEntries(cartDto.getCartEntries().stream().map(cartEntryMapper::toEntity).toList());
        cart.setTotalPrice(cartDto.getTotalPrice());

        return cart;
    }
}
