package com.example.shopbackend.mapper.OrderRelatedMappers;

import com.example.shopbackend.dto.CartEntryDto;
import com.example.shopbackend.entity.CartEntry;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class CartEntryMapper {
    private final ProductMapper productMapper;

    public CartEntryMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public CartEntryDto toDto(CartEntry cartEntry) {
        CartEntryDto cartEntryDto = new CartEntryDto();
        cartEntryDto.setId(cartEntry.getId());
        cartEntryDto.setProduct(productMapper.toDto(cartEntry.getProduct()));
        cartEntryDto.setPricePerPiece(cartEntry.getPricePerPiece());
        cartEntryDto.setQuantity(cartEntry.getQuantity());
        cartEntryDto.setTotalPrice(cartEntry.getTotalPrice());

        return cartEntryDto;
    }

    public CartEntry toEntity(CartEntryDto cartEntryDto) {
        CartEntry cartEntry = new CartEntry();
        cartEntry.setId(cartEntryDto.getId());
        cartEntry.setProduct(productMapper.toEntity(cartEntryDto.getProduct()));
        cartEntry.setPricePerPiece(cartEntryDto.getPricePerPiece());
        cartEntry.setQuantity(cartEntryDto.getQuantity());
        cartEntry.setTotalPrice(cartEntryDto.getTotalPrice());

        return cartEntry;
    }
}
