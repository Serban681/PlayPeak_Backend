package com.example.shopbackend.service;

import com.example.shopbackend.dto.OrderDto;
import com.example.shopbackend.dto.OrderRequest;
import com.example.shopbackend.entity.Order;
import com.example.shopbackend.exceptions.EntityNotFoundException;
import com.example.shopbackend.mapper.AddressMapper;
import com.example.shopbackend.mapper.OrderRelatedMappers.CartMapper;
import com.example.shopbackend.mapper.OrderRelatedMappers.OrderMapper;
import com.example.shopbackend.mapper.UserMapper;
import com.example.shopbackend.repository.OrderRepository;
import com.example.shopbackend.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final EmailService emailService;
    private final CartService cartService;

    private OrderService(OrderRepository orderRepository,
                         OrderMapper orderMapper,
                         CartMapper cartMapper,
                         UserRepository userRepository,
                         UserMapper userMapper,
                         AddressMapper addressMapper,
                         EmailService emailService,
                         CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartMapper = cartMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
        this.emailService = emailService;
        this.cartService = cartService;
    }

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream().map(orderMapper::entityToDto).toList();
    }

    public OrderDto create(OrderRequest orderRequest) throws MessagingException {
        Order order = new Order();
        order.setUser(userRepository.findById(orderRequest.getUserId()));
        order.setCart(cartMapper.dtoToEntity(orderRequest.getCart()));
        order.setPaymentType(orderRequest.getPaymentType());
        order.setDeliveryAddress(addressMapper.toEntity(orderRequest.getDeliveryAddress()));
        order.setBillingAddress(addressMapper.toEntity(orderRequest.getBillingAddress()));
        order.setOrderDate(LocalDate.now());

        String orderedProducts = "";

        for (var cartEntry : order.getCart().getCartEntries()) {
            orderedProducts += "<div style=\"display: inline-block; margin: 10px; text-align: center;\">" +
                    "<img src=\"" + cartEntry.getProductVariance().getProduct().getPhotoUrl() + "\" alt=\"" + cartEntry.getProductVariance().getProduct().getName() + "\" style=\"width: 100px; height: 100px; display: block; margin-bottom: 5px;\"/>" +
                    "<span style=\"font-weight: bold;\">" + cartEntry.getProductVariance().getProduct().getName() + "</span>" +
                    "<br/>" + cartEntry.getQuantity() + " x " + cartEntry.getProductVariance().getProduct().getPrice() + "$" +
                    "</div>";
        }

        emailService.sendEmail(
                order.getUser().getEmail(),
                "order@cool-shop.com",
                "Order confirmation",
                "<div style=\"font-family: Arial, sans-serif; color: #333;\">" +
                        "<h1 style=\"color: #F8B8ED;\">Order Confirmation</h1>" +
                        "<p>Dear " + order.getUser().getFirstName() + ",</p>" +
                        "<p>Your order has been successfully placed. Thank you for shopping with us!</p>" +
                        "<p><strong>Here is what you ordered:</strong></p>" +
                        "<div style=\"display: flex; flex-wrap: wrap;\">" + orderedProducts + "</div>" +
                        "<p><strong>Delivery Address:</strong><br/>" +
                        order.getDeliveryAddress().getStreetLine() + ", " +
                        order.getDeliveryAddress().getCity() + ", " +
                        order.getDeliveryAddress().getCountry() + "</p>" +
                        "<p><strong>Billing Address:</strong><br/>" +
                        order.getBillingAddress().getStreetLine() + ", " +
                        order.getBillingAddress().getCity() + ", " +
                        order.getBillingAddress().getCountry() + "</p>" +
                        "<p>Your order will be delivered in 3-5 business days.</p>" +
                        "<p><strong>Total price: </strong>" + order.getCart().getTotalPrice() + "$</p>" +
                        "<p style=\"font-size: 12px; color: #888;\">If you have any questions, feel free to contact our support team.</p>" +
                        "<p>Best regards,<br/>Cool Shop Team</p>" +
                        "</div>"
        );


        return orderMapper.entityToDto(orderRepository.save(order));
    }

    public List<OrderDto> createMany(List<OrderRequest> orderRequests) throws MessagingException {
        return orderRequests.stream().map(orderRequest -> {
            Order order = new Order();

            order.setUser(userRepository.findById(orderRequest.getUserId()));
            order.setCart(cartMapper.dtoToEntity(orderRequest.getCart()));
            order.setPaymentType(orderRequest.getPaymentType());
            order.setDeliveryAddress(addressMapper.toEntity(orderRequest.getDeliveryAddress()));
            order.setBillingAddress(addressMapper.toEntity(orderRequest.getBillingAddress()));
            order.setOrderDate(orderRequest.getOrderDate());

            return orderMapper.entityToDto(orderRepository.save(order));
        }).toList();
    }

    public void deleteAll() {
        List<Order> orders = orderRepository.findAll();
        orderRepository.deleteAll();
        orders.forEach(order -> cartService.deleteCart(order.getCart().getId()));
    }

    public void delete(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order", id));
        orderRepository.deleteById(id);
        cartService.deleteCart(order.getCart().getId());
    }

    public void deleteByUserId(Integer userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        orderRepository.deleteAllByUserId(userId);
        orders.forEach(order -> cartService.deleteCart(order.getCart().getId()));
    }
}
