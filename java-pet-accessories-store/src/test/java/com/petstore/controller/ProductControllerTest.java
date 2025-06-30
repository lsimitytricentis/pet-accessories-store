package com.petstore.controller;

import com.petstore.model.Product;
import com.petstore.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product testProduct1;
    private Product testProduct2;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        testProduct1 = new Product("Dog Toy", "Fun toy for dogs", 
            new BigDecimal("15.99"), "http://example.com/dog-toy.jpg", 
            Product.Category.TOYS, 25);
        testProduct1.setId(1L);

        testProduct2 = new Product("Cat Food", "Premium cat food", 
            new BigDecimal("29.99"), "http://example.com/cat-food.jpg", 
            Product.Category.FOOD, 5);
        testProduct2.setId(2L);

        testProducts = Arrays.asList(testProduct1, testProduct2);
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(testProducts);

        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Dog Toy"))
                .andExpect(jsonPath("$[0].price").value(15.99))
                .andExpect(jsonPath("$[1].name").value("Cat Food"))
                .andExpect(jsonPath("$[1].price").value(29.99));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Found() throws Exception {
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(Optional.of(testProduct1));

        mockMvc.perform(get("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Dog Toy"))
                .andExpect(jsonPath("$.price").value(15.99))
                .andExpect(jsonPath("$.category").value("TOYS"));

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        Long productId = 999L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testGetProductsByCategory() throws Exception {
        Product.Category category = Product.Category.TOYS;
        List<Product> toyProducts = Arrays.asList(testProduct1);
        when(productService.getProductsByCategory(category)).thenReturn(toyProducts);

        mockMvc.perform(get("/api/products/category/{category}", category)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Dog Toy"))
                .andExpect(jsonPath("$[0].category").value("TOYS"));

        verify(productService, times(1)).getProductsByCategory(category);
    }

    @Test
    void testSearchProducts() throws Exception {
        String query = "dog";
        List<Product> searchResults = Arrays.asList(testProduct1);
        when(productService.searchProducts(query)).thenReturn(searchResults);

        mockMvc.perform(get("/api/products/search")
                .param("q", query)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Dog Toy"));

        verify(productService, times(1)).searchProducts(query);
    }

    @Test
    void testSearchProducts_EmptyResults() throws Exception {
        String query = "nonexistent";
        when(productService.searchProducts(query)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/products/search")
                .param("q", query)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(productService, times(1)).searchProducts(query);
    }
}