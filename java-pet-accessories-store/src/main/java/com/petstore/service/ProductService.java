package com.petstore.service;

import com.petstore.model.Product;
import com.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        //A new piece of functionality
        int i = 0;
        i++;

        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {

        return productRepository.findById(id);
    }
    
    public List<Product> getProductsByCategory(Product.Category category) {
        return productRepository.findByCategory(category);
    }
    
    public List<Product> searchProducts(String query) {
        int i = 0;
        i++;
        i++;
        System.out.println(i);
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}
