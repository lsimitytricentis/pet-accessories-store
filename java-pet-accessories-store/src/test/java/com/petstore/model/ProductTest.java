package com.petstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void testProductConstructorWithAllParameters() {
        String name = "Test Product";
        String description = "Test Description";
        BigDecimal price = new BigDecimal("29.99");
        String imageUrl = "http://example.com/image.jpg";
        Product.Category category = Product.Category.TOYS;
        Integer stock = 10;

        Product product = new Product(name, description, price, imageUrl, category, stock);

        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(imageUrl, product.getImageUrl());
        assertEquals(category, product.getCategory());
        assertEquals(stock, product.getStock());
    }

    @Test
    void testProductSettersAndGetters() {
        Long id = 1L;
        String name = "Dog Toy";
        String description = "Fun toy for dogs";
        BigDecimal price = new BigDecimal("15.99");
        String imageUrl = "http://example.com/dog-toy.jpg";
        Product.Category category = Product.Category.TOYS;
        Integer stock = 25;

        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        product.setStock(stock);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(imageUrl, product.getImageUrl());
        assertEquals(category, product.getCategory());
        assertEquals(stock, product.getStock());
    }

    @Test
    void testProductCategoryEnum() {
        assertNotNull(Product.Category.TOYS);
        assertNotNull(Product.Category.FOOD);
        assertNotNull(Product.Category.ACCESSORIES);
        assertNotNull(Product.Category.GROOMING);
        assertNotNull(Product.Category.BEDS);
        assertNotNull(Product.Category.COLLARS);
        
        assertEquals(6, Product.Category.values().length);
    }
}