package com.example.shopbackend.service;

import com.example.shopbackend.dto.CartDto;
import com.example.shopbackend.dto.CartEntryDto;
import com.example.shopbackend.entity.Cart;
import com.example.shopbackend.entity.CartEntry;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.OrderRelatedMappers.CartEntryMapper;
import com.example.shopbackend.mapper.OrderRelatedMappers.CartMapper;
import com.example.shopbackend.repository.CartEntryRepository;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartEntryService {
    private final CartEntryRepository cartEntryRepository;
    private final CartEntryMapper cartEntryMapper;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartEntryService(
            CartEntryRepository cartEntryRepository,
            CartEntryMapper cartEntryMapper,
            ProductRepository productRepository,
            CartService cartService,
            CartMapper cartMapper
    ) {
        this.cartEntryRepository = cartEntryRepository;
        this.cartEntryMapper = cartEntryMapper;
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    public List<CartEntryDto> getAll() {
        List<CartEntry> cartEntries = cartEntryRepository.findAll();
        return cartEntries.stream().map(cartEntryMapper::toDto).toList();
    }

    public CartEntryDto getOneById(int id) {
        return cartEntryMapper.toDto(cartEntryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart Entry not found", id)));
    }
    @Transactional
    public CartEntryDto create(int productId, int cartId) {
//        CartEntry cartEntry = new CartEntry();
//        cartEntry.setProduct(productRepository.findProductById(productId));
//        cartEntry.setQuantity(1);
//        cartEntry.setPricePerPiece(cartEntry.getProduct().getPrice());
//        cartEntry.setTotalPrice(cartEntry.getPricePerPiece() * cartEntry.getQuantity());
//
//        CartEntry savedCartEntry = cartEntryRepository.save(cartEntry);
//
//        cartService.assignCartEntry(cartId, cartEntryMapper.toDto(savedCartEntry));
//
//        return cartEntryMapper.toDto(savedCartEntry);
        return  null;
    }

    public CartEntryDto updateQuantity(int cartEntryId, int quantity) {
        CartEntry cartEntry = cartEntryRepository.findById(cartEntryId).orElseThrow(() -> new EntityNotFoundException("Cart Entry not found", cartEntryId));
        cartEntry.setQuantity(quantity);
        cartEntry.setTotalPrice(cartEntry.getPricePerPiece() * quantity);
        return cartEntryMapper.toDto(cartEntryRepository.save(cartEntry));
    }

    public void removeCartEntry(int cartEntryId, int cartId) {
//        CartDto cartDto = cartService.getOneById(cartId);
//
//        Cart cart = cartMapper.toEntity(cartDto);
//
//        List<CartEntry> newCartEntries = cart.getCartEntries().stream()
//                .filter(entry -> entry.getId() != cartEntryId)
//                .toList();
//
//        cart.setCartEntries(newCartEntries);
//        cart.setTotalPrice(cart.getTotalPrice() - cartEntryRepository.findById(cartEntryId).orElseThrow(() -> new EntityNotFoundException("Cart Entry: ", cartEntryId)).getTotalPrice());
//
//        cartService.updateCart(cartMapper.toDto(cart));
//        cartEntryRepository.deleteById(cartEntryId);

    }

    public void delete(Integer id) {
        cartEntryRepository.deleteById(id);
    }
}
