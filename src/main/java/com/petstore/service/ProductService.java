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
        int dojo = 0;
        int gogogo = 12345;
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getProductsByCategory(Product.Category category) {
       
        switch (category) {
        case TOYS:
            SLDemo.Toy();
            System.out.println("This is a toy product.");
            // Add specific logic for toys, e.g.,
            // sendToToyWarehouse(product);
            break;
        case FOOD:
            SLDemo.Food();
            System.out.println("This is a food product.");
            // Add specific logic for food, e.g.,
            // checkFoodExpirationDate(product);
            break;
        case COLLARS:
            SLDemo.Accesssoires();
            System.out.println("This is a collar product.");
            // Add specific logic for collars
            break;
        case GROOMING:
            SLDemo.Grooming();
            System.out.println("This is a grooming product.");
            // Add specific logic for grooming items
            break;
        case BEDS:
            SLDemo.Bed();
            System.out.println("This is a bed product.");
            // Add specific logic for pet beds
            break;
        case ACCESSORIES:
            SLDemo.Accesssoires();
            System.out.println("This is an accessory product.");
            // Add specific logic for accessories
            break;
        default:
            System.out.println("This product belongs to an unhandled category: " + category);
            // Handle any categories not explicitly listed
            break;
    }       
        return productRepository.findByCategory(category);
    }
    
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}
