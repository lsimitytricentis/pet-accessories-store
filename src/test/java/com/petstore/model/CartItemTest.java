package com.petstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        cartItem = new CartItem();
    }

    @Test
    void testCartItemConstructorWithAllParameters() {
        Long productId = 1L;
        String name = "Test Product";
        BigDecimal price = new BigDecimal("19.99");
        Integer quantity = 2;
        String imageUrl = "http://example.com/image.jpg";

        CartItem item = new CartItem(productId, name, price, quantity, imageUrl);

        assertEquals(productId, item.getProductId());
        assertEquals(name, item.getName());
        assertEquals(price, item.getPrice());
        assertEquals(quantity, item.getQuantity());
        assertEquals(imageUrl, item.getImageUrl());
    }

    @Test
    void testCartItemSettersAndGetters() {
        Long productId = 1L;
        String name = "Cat Food";
        BigDecimal price = new BigDecimal("24.99");
        Integer quantity = 3;
        String imageUrl = "http://example.com/cat-food.jpg";

        cartItem.setProductId(productId);
        cartItem.setName(name);
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);
        cartItem.setImageUrl(imageUrl);

        assertEquals(productId, cartItem.getProductId());
        assertEquals(name, cartItem.getName());
        assertEquals(price, cartItem.getPrice());
        assertEquals(quantity, cartItem.getQuantity());
        assertEquals(imageUrl, cartItem.getImageUrl());
    }

    @Test
    void testGetTotal() {
        BigDecimal price = new BigDecimal("15.50");
        Integer quantity = 4;
        
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);

        BigDecimal expectedTotal = price.multiply(BigDecimal.valueOf(quantity));
        assertEquals(expectedTotal, cartItem.getTotal());
        assertEquals(new BigDecimal("62.00"), cartItem.getTotal());
    }

    @Test
    void testGetTotalWithSingleItem() {
        BigDecimal price = new BigDecimal("29.99");
        Integer quantity = 1;
        
        cartItem.setPrice(price);
        cartItem.setQuantity(quantity);

        assertEquals(price, cartItem.getTotal());
    }
}