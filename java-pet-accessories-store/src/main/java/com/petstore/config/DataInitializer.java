package com.petstore.config;

import com.petstore.model.Product;
import com.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            // Sample products
            productRepository.save(new Product("Squeaky Bone Toy", "Durable rubber bone toy that squeaks", 
                new BigDecimal("12.99"), "https://images.unsplash.com/photo-1601758228041-f3b2795255f1?w=400", 
                Product.Category.TOYS, 50));
                
            productRepository.save(new Product("Premium Dog Food", "High-quality nutrition for your furry friend", 
                new BigDecimal("45.99"), "https://images.unsplash.com/photo-1589924691995-400dc9ecc119?w=400", 
                Product.Category.FOOD, 25));
                
            productRepository.save(new Product("Leather Dog Collar", "Stylish and comfortable leather collar", 
                new BigDecimal("24.99"), "https://images.unsplash.com/photo-1583337130417-3346a1be7dee?w=400", 
                Product.Category.COLLARS, 30));
                
            productRepository.save(new Product("Cat Scratching Post", "Multi-level scratching post with toys", 
                new BigDecimal("89.99"), "https://images.unsplash.com/photo-1574158622682-e40e69881006?w=400", 
                Product.Category.TOYS, 15));
                
            productRepository.save(new Product("Pet Grooming Kit", "Complete grooming set with brushes and nail clippers", 
                new BigDecimal("34.99"), "https://images.unsplash.com/photo-1581888227599-779811939961?w=400", 
                Product.Category.GROOMING, 40));
                
            productRepository.save(new Product("Cozy Pet Bed", "Ultra-soft and washable pet bed", 
                new BigDecimal("59.99"), "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?w=400", 
                Product.Category.BEDS, 20));
                
            productRepository.save(new Product("Interactive Puzzle Toy", "Mental stimulation toy for intelligent pets", 
                new BigDecimal("19.99"), "https://images.unsplash.com/photo-1605568427561-40dd23c2acea?w=400", 
                Product.Category.TOYS, 35));
                
            productRepository.save(new Product("Retractable Dog Leash", "16ft retractable leash with brake system", 
                new BigDecimal("28.99"), "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=400", 
                Product.Category.ACCESSORIES, 45));
        }
    }
}