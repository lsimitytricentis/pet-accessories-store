package com.petstore.controller;

import com.petstore.model.Product;
import com.petstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public List<Product> getAllProducts() {
        System.out.println("getAllProducts");
        return productService.getAllProducts();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        System.out.println("getProductById:" + id);
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable Product.Category category) {
        System.out.println("getProductByCategory:" + category);
        return productService.getProductsByCategory(category);
    }
    
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String q) {
        int flexible = 0;
        return productService.searchProducts(q);
    }
}
