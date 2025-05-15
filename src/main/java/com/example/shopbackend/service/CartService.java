package com.example.shopbackend.service;

import com.example.shopbackend.dto.CartDto;
import com.example.shopbackend.entity.Cart;
import com.example.shopbackend.entity.CartEntry;
import com.example.shopbackend.entity.ProductVariance;
import com.example.shopbackend.entity.User;
import com.example.shopbackend.exceptions.EmptyStockException;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.OrderRelatedMappers.CartMapper;
import com.example.shopbackend.mapper.UserMapper;
import com.example.shopbackend.repository.CartEntryRepository;
import com.example.shopbackend.repository.CartRepository;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductRepository;
import com.example.shopbackend.repository.ProductRelatedRepositories.ProductVarianceRepository;
import com.example.shopbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final CartEntryRepository cartEntryRepository;
    private final ProductVarianceRepository productVarianceRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProductVarianceService productVarianceService;

    public CartService(
            CartMapper cartMapper,
            CartRepository cartRepository,
            ProductVarianceRepository productVarianceRepository,
            CartEntryRepository cartEntryRepository,
            UserRepository userRepository,
            UserMapper userMapper,
            ProductVarianceService productVarianceService
    ) {
        this.cartMapper = cartMapper;
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
        this.productVarianceRepository = productVarianceRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.productVarianceService = productVarianceService;
    }

    public List<CartDto> getAll() {
        return cartRepository.findAll().stream()
                .map(cartMapper::entityToDto)
                .toList();
    }

    public CartDto getCartByUserId(int userId) {
        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart == null) {
            return createCart(userId);
        }

        return cartMapper.entityToDto(cartRepository.findCartByUserId(userId));
    }

    public void updateAvailableQuantityOfOrderedProducts(int cartId) {
        Cart cart = cartRepository.findCartById(cartId);

        cart.getCartEntries().forEach(cartEntry -> {
            ProductVariance productVariance = cartEntry.getProductVariance();

            productVarianceService.subtractQuantity(productVariance.getId(), cartEntry.getQuantity());
        });
    }

    public CartDto removeCartFromUser(int userId) {
        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart != null) {
            cart.setUser(null);

            return cartMapper.entityToDto(cartRepository.save(cart));
        }

        return null;
    }

    public CartDto createCart(int userId) {
        User user = userRepository.findById(userId);

        if(user == null) {
            throw new EntityNotFoundException("User", userId);
        }

        CartDto cartDto = new CartDto();
        cartDto.setUser(userMapper.toDto(user));
        cartDto.setCartEntries(new ArrayList<>());
        cartDto.setTotalPrice(0);

        return cartMapper.entityToDto(cartRepository.save(cartMapper.dtoToEntity(cartDto)));
    }

    public CartDto createMockCart() {
        Cart cart = new Cart();
        cart.setUser(null);
        cart.setCartEntries(new ArrayList<>());
        cart.setTotalPrice(0);

        return cartMapper.entityToDto(cartRepository.save(cart));
    }

    public CartDto getOneById(int id) {
        return cartMapper.entityToDto(cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found", id)));
    }

    @Transactional
    public CartDto addProductToCart(int productVarianceId, int cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found", cartId));

        ProductVariance productVariance = productVarianceRepository.findById(productVarianceId);

        if(productVariance == null) {
            throw new EntityNotFoundException("Product Variance", productVarianceId);
        }

        CartEntry cartEntry = cart.getCartEntries().stream().filter(entry -> entry.getProductVariance().getId() == productVarianceId).findFirst().orElse(null);

        if (cartEntry != null && productVariance.getQuantity() - cartEntry.getQuantity() > 0) {
            cartEntry.setQuantity(cartEntry.getQuantity() + 1);
            cartEntry.setTotalPrice(cartEntry.getPricePerPiece() * cartEntry.getQuantity());

            cartEntryRepository.save(cartEntry);

            cart.setTotalPrice(cart.getTotalPrice() + cartEntry.getPricePerPiece());

            return cartMapper.entityToDto(cartRepository.save(cart));
        } else if(cartEntry == null && productVariance.getQuantity() > 0) {
            cartEntry = new CartEntry();
            cartEntry.setProductVariance(productVariance);
            cartEntry.setQuantity(1);
            cartEntry.setPricePerPiece(cartEntry.getProductVariance().getProduct().getPrice());
            cartEntry.setTotalPrice(cartEntry.getPricePerPiece() * cartEntry.getQuantity());

            CartEntry savedCartEntry = cartEntryRepository.save(cartEntry);

            List<CartEntry> currentCartEntries = cart.getCartEntries();
            currentCartEntries.add(savedCartEntry);
            cart.setCartEntries(currentCartEntries);
            cart.setTotalPrice(cart.getTotalPrice() + cartEntry.getTotalPrice());

            return cartMapper.entityToDto(cartRepository.save(cart));
        } else {
            throw new EmptyStockException();
        }
    }

    public CartDto removeProductFromCart(int cartEntryId) {
        Cart cart = cartRepository.findCartByCartEntryId(cartEntryId);

        if(cart == null) {
            throw new EntityNotFoundException("Cart", cartEntryId);
        }

        CartEntry cartEntry = cartEntryRepository.findById(cartEntryId).orElseThrow(() -> new EntityNotFoundException("Cart entry", cartEntryId));
        cart.setTotalPrice(cart.getTotalPrice() - cartEntry.getTotalPrice());
        cart.setCartEntries(cart.getCartEntries().stream().filter(entry -> entry.getId() != cartEntryId).toList());

        cartEntryRepository.delete(cartEntry);
        return cartMapper.entityToDto(cart);
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

        return cartMapper.entityToDto(cartRepository.save(cart));
    }

//    public CartDto updateCart(CartDto cartDto) {
//        return cartMapper.toDto(cartRepository.save(cartMapper.toEntity(cartDto)));
//    }

    public void deleteCart(int id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if(cart != null) {
            List<CartEntry> cartEntries = cart.getCartEntries();
            cartEntries.forEach(cartEntry -> cartEntryRepository.deleteById(cartEntry.getId()));
            cartRepository.deleteById(id);
        }
    }
}
