package com.petstore.service;

import com.petstore.model.Product;
import com.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getProductsByCategory(Product.Category category) {
        return productRepository.findByCategory(category);
    }
    
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}
