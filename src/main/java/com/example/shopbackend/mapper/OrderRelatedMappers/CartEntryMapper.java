package com.example.shopbackend.mapper.OrderRelatedMappers;

import com.example.shopbackend.dto.CartEntryDto;
import com.example.shopbackend.entity.CartEntry;
import com.example.shopbackend.mapper.ProductRelatedMappers.ProductVarianceMapper;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductVarianceRepository;
import org.springframework.stereotype.Service;

@Service
public class CartEntryMapper {
    private final ProductVarianceMapper productVarianceMapper;

    public CartEntryMapper(ProductVarianceMapper productVarianceMapper) {
        this.productVarianceMapper = productVarianceMapper;
    }

    public CartEntryDto toDto(CartEntry cartEntry) {
        CartEntryDto cartEntryDto = new CartEntryDto();
        cartEntryDto.setId(cartEntry.getId());
        cartEntryDto.setProductVariance(productVarianceMapper.entityToRequest(cartEntry.getProductVariance()));
        cartEntryDto.setPricePerPiece(cartEntry.getPricePerPiece());
        cartEntryDto.setQuantity(cartEntry.getQuantity());
        cartEntryDto.setTotalPrice(cartEntry.getTotalPrice());

        return cartEntryDto;
    }

    public CartEntry toEntity(CartEntryDto cartEntryDto) {
        CartEntry cartEntry = new CartEntry();
        cartEntry.setId(cartEntryDto.getId());
        cartEntry.setProductVariance(productVarianceMapper.requestToEntity(cartEntryDto.getProductVariance()));
        cartEntry.setPricePerPiece(cartEntryDto.getPricePerPiece());
        cartEntry.setQuantity(cartEntryDto.getQuantity());
        cartEntry.setTotalPrice(cartEntryDto.getTotalPrice());

        return cartEntry;
    }
}
