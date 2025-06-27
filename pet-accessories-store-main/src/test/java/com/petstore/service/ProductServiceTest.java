package com.petstore.service;

import com.petstore.model.Product;
import com.petstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct1;
    private Product testProduct2;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        testProduct1 = new Product("Dog Toy", "Fun toy for dogs", 
            new BigDecimal("15.99"), "http://example.com/dog-toy.jpg", 
            Product.Category.TOYS, 10);
        testProduct1.setId(1L);

        testProduct2 = new Product("Cat Food", "Premium cat food", 
            new BigDecimal("29.99"), "http://example.com/cat-food.jpg", 
            Product.Category.FOOD, 5);
        testProduct2.setId(2L);

        testProducts = Arrays.asList(testProduct1, testProduct2);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(testProducts);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testProduct1, result.get(0));
        assertEquals(testProduct2, result.get(1));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Found() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct1));

        Optional<Product> result = productService.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(testProduct1, result.get());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductById_NotFound() {
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(productId);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductsByCategory() {
        Product.Category category = Product.Category.TOYS;
        List<Product> toyProducts = Arrays.asList(testProduct1);
        when(productRepository.findByCategory(category)).thenReturn(toyProducts);

        List<Product> result = productService.getProductsByCategory(category);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProduct1, result.get(0));
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    void testSearchProducts() {
        String query = "dog";
        List<Product> searchResults = Arrays.asList(testProduct1);
        when(productRepository.findByNameContainingIgnoreCase(query)).thenReturn(searchResults);

        List<Product> result = productService.searchProducts(query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProduct1, result.get(0));
        verify(productRepository, times(1)).findByNameContainingIgnoreCase(query);
    }

    @Test
    void testSearchProducts_NoResults() {
        String query = "nonexistent";
        when(productRepository.findByNameContainingIgnoreCase(query)).thenReturn(Arrays.asList());

        List<Product> result = productService.searchProducts(query);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findByNameContainingIgnoreCase(query);
    }
}