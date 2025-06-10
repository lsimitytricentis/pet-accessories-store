package com.petstore.integration;

import com.petstore.PetAccessoriesStoreApplication;
import com.petstore.model.Product;
import com.petstore.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PetAccessoriesStoreApplication.class)
@AutoConfigureWebMvc
@ActiveProfiles("test")
class PetStoreIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFullApplicationFlow() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Test that products are loaded by DataInitializer
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(8)); // 8 sample products

        // Test category filtering
        mockMvc.perform(get("/api/products/category/TOYS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("TOYS"));

        // Test search functionality
        mockMvc.perform(get("/api/products/search").param("q", "dog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNumber());

        // Test individual product retrieval
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        // Test web controller redirect
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/index.html"));
    }

    @Test
    void testDataInitializerLoadsProducts() {
        long productCount = productRepository.count();
        assertEquals(8, productCount, "DataInitializer should load 8 sample products");

        // Verify specific products exist
        assertTrue(productRepository.findByNameContainingIgnoreCase("Squeaky Bone").size() > 0);
        assertTrue(productRepository.findByNameContainingIgnoreCase("Premium Dog Food").size() > 0);
        assertTrue(productRepository.findByCategory(Product.Category.TOYS).size() > 0);
    }
}