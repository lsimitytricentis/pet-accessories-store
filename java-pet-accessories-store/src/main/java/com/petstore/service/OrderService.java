package com.petstore.service;

import com.petstore.model.*;
import com.petstore.repository.OrderRepository;
import com.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserService userService;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
     
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public Optional<Order> getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
    
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<Order> getOrdersByEmail(String email) {
        return orderRepository.findByUserEmailOrderByCreatedAtDesc(email);
    }
    
    @Transactional
    public Order createDraftOrder(OrderRequest orderRequest) {
        int i =0;
        i++;

        // Find or create user
        User user = userService.findOrCreateUser(
            orderRequest.getEmail(),
            orderRequest.getFirstName(),
            orderRequest.getLastName()
        );
        
        // Update user address if provided
        if (orderRequest.getAddress() != null) {
            user.setAddress(orderRequest.getAddress());
            user.setCity(orderRequest.getCity());
            user.setZipCode(orderRequest.getZipCode());
            user.setCountry(orderRequest.getCountry());
            userService.updateUser(user);
        }
        
        // Create draft order
        Order order = new Order();
        order.setOrderNumber("DRAFT" + System.currentTimeMillis());
        order.setUser(user);
        order.setShippingAddress(orderRequest.getAddress());
        order.setShippingCity(orderRequest.getCity());
        order.setShippingZipCode(orderRequest.getZipCode());
        order.setShippingCountry(orderRequest.getCountry());
        order.setShippingMethod(orderRequest.getShippingMethod());
        order.setShippingCost(orderRequest.getShippingCost());
        order.setStatus(Order.OrderStatus.DRAFT);
        
        // Calculate totals and create order items
        BigDecimal subtotal = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (CartItemRequest cartItem : orderRequest.getCartItems()) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + cartItem.getProductId()));
            
            // Check stock availability (don't reduce yet)
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            
            // Create order item
            OrderItem orderItem = new OrderItem(order, product, cartItem.getQuantity(), product.getPrice());
            //OrderItem orderItem = new OrderItem(product, cartItem.getQuantity(), product.getPrice());
            orderItems.add(orderItem);
            
            subtotal = subtotal.add(orderItem.getTotalPrice());
        }
        
        order.setSubtotal(subtotal);
        order.setTotalAmount(subtotal.add(order.getShippingCost()));
        order.setOrderItems(orderItems);
        
        return orderRepository.save(order);
    }
    
    @Transactional
    public Order confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
                
        /*if (order.getStatus() != Order.OrderStatus.DRAFT) {
            throw new RuntimeException("Order cannot be confirmed in current status: " + order.getStatus());
        }*/
        
        // Reduce stock for all items
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            if (product.getStock() < orderItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            product.setStock(product.getStock() - orderItem.getQuantity());
            productRepository.save(product);
        }
        
        // Update order status and number
        order.setStatus(Order.OrderStatus.CONFIRMED);
        order.setOrderNumber("PET" + System.currentTimeMillis());
        
      return orderRepository.save(order);
    }
    
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        
        if (status == Order.OrderStatus.SHIPPED && order.getShippedAt() == null) {
            order.setShippedAt(LocalDateTime.now());
        } else if (status == Order.OrderStatus.DELIVERED && order.getDeliveredAt() == null) {
            order.setDeliveredAt(LocalDateTime.now());
        }
        
        return orderRepository.save(order);
    }
    
    // Helper classes for order creation
    public static class OrderRequest {
        private String email;
        private String firstName;
        private String lastName;
        private String address;
        private String city;
        private String zipCode;
        private String country;
        private String shippingMethod;
        private BigDecimal shippingCost;
        private List<CartItemRequest> cartItems;
        
        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        
        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        
        public String getShippingMethod() { return shippingMethod; }
        public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }
        
        public BigDecimal getShippingCost() { return shippingCost; }
        public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; }
        
        public List<CartItemRequest> getCartItems() { return cartItems; }
        public void setCartItems(List<CartItemRequest> cartItems) { this.cartItems = cartItems; }
    }
    
    public static class CartItemRequest {
        private Long productId;
        private Integer quantity;
        
        // Getters and Setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}