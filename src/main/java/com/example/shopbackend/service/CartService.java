package com.example.shopbackend.service;

import com.example.shopbackend.dto.CartDto;
import com.example.shopbackend.dto.CartEntryDto;
import com.example.shopbackend.entity.Cart;
import com.example.shopbackend.entity.CartEntry;
import com.example.shopbackend.entity.Product;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.OrderRelatedMappers.CartMapper;
import com.example.shopbackend.repository.CartEntryRepository;
import com.example.shopbackend.repository.CartRepository;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final UserService userService;
//    private final CartEntryMapper cartEntryMapper;
    private final CartEntryRepository cartEntryRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartMapper cartMapper,
            CartRepository cartRepository,
            UserService userService,
//            CartEntryMapper cartEntryMapper
            CartEntryRepository cartEntryRepository,
            ProductRepository productRepository
    ) {
        this.cartMapper = cartMapper;
        this.cartRepository = cartRepository;
        this.userService = userService;
//        this.cartEntryMapper = cartEntryMapper;
        this.cartEntryRepository = cartEntryRepository;
        this.productRepository = productRepository;
    }

    public List<CartDto> getAll() {
        return cartRepository.findAll().stream()
                .map(cartMapper::toDto)
                .toList();
    }

    public CartDto getCartByUserId(int userId) {
        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart == null) {
            return createCart(userId);
        }

        return cartMapper.toDto(cartRepository.findCartByUserId(userId));
    }

    public CartDto getOneById(int id) {
        return cartMapper.toDto(cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found", id)));
    }

    public CartDto createCart(int userId) {
        CartDto cartDto = new CartDto();
        cartDto.setUser(userService.getOneById(userId));
        cartDto.setCartEntries(new ArrayList<>());
        cartDto.setTotalPrice(0);

        return cartMapper.toDto(cartRepository.save(cartMapper.toEntity(cartDto)));
    }

    @Transactional
    public CartDto addProductToCart(int productId, int cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found", cartId));

        Product product = productRepository.findProductById(productId);

        if(product == null) {
            throw new EntityNotFoundException("Product not found", productId);
        }

        CartEntry cartEntry = cart.getCartEntries().stream().filter(entry -> entry.getProduct().getId() == productId).findFirst().orElse(null);

        if(cartEntry != null) {
            return cartMapper.toDto(cart);
        }

        cartEntry = new CartEntry();
        cartEntry.setProduct(product);
        cartEntry.setQuantity(1);
        cartEntry.setPricePerPiece(cartEntry.getProduct().getPrice());
        cartEntry.setTotalPrice(cartEntry.getPricePerPiece() * cartEntry.getQuantity());

        CartEntry savedCartEntry = cartEntryRepository.save(cartEntry);

        List<CartEntry> currentCartEntries = cart.getCartEntries();
        currentCartEntries.add(savedCartEntry);
        cart.setCartEntries(currentCartEntries);
        cart.setTotalPrice(cart.getTotalPrice() + cartEntry.getTotalPrice());

        return cartMapper.toDto(cartRepository.save(cart));
    }

    public CartDto removeProductFromCart(int cartEntryId) {
        Cart cart = cartRepository.findCartByCartEntryId(cartEntryId);

        if(cart == null) {
            throw new EntityNotFoundException("Cart with cart entry id not found", cartEntryId);
        }

        CartEntry cartEntry = cartEntryRepository.findById(cartEntryId).orElseThrow(() -> new EntityNotFoundException("Cart entry not found", cartEntryId));
        cart.setTotalPrice(cart.getTotalPrice() - cartEntry.getTotalPrice());
        cart.setCartEntries(cart.getCartEntries().stream().filter(entry -> entry.getId() != cartEntryId).toList());

        cartEntryRepository.delete(cartEntry);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    public CartDto modifyCartEntryQuantity(int cartEntryId, int quantity) {
        Cart cart = cartRepository.findCartByCartEntryId(cartEntryId);

        if(cart == null) {
            throw new EntityNotFoundException("Cart with cart entry id not found", cartEntryId);
        }

        CartEntry cartEntry = cartEntryRepository.findById(cartEntryId).orElseThrow(() -> new EntityNotFoundException("Cart entry not found", cartEntryId));

        cartEntry.setQuantity(quantity);
        cartEntry.setTotalPrice(cartEntry.getPricePerPiece() * cartEntry.getQuantity());
        cartEntryRepository.save(cartEntry);

        cart.setTotalPrice(cart.getCartEntries().stream().map(CartEntry::getTotalPrice).reduce(0f, Float::sum));

        return cartMapper.toDto(cartRepository.save(cart));
    }

//    public CartDto updateCart(CartDto cartDto) {
//        return cartMapper.toDto(cartRepository.save(cartMapper.toEntity(cartDto)));
//    }

    public void deleteCart(int id) {
        cartRepository.deleteById(id);
    }
}
