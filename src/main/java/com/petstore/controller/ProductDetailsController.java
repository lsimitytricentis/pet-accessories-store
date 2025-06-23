package com.petstore.controller;

import com.petstore.model.Product;
import com.petstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/product-details")
@CrossOrigin(origins = "*")
public class ProductDetailsController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductDetails(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("product", product);
                    
                    // Add related products
                    List<Product> relatedProducts = productService.getProductsByCategory(product.getCategory())
                            .stream()
                            .filter(p -> !p.getId().equals(id))
                            .limit(4)
                            .toList();
                    response.put("relatedProducts", relatedProducts);
                    
                    // Add category-specific features
                    response.put("features", getCategoryFeatures(product.getCategory()));
                    
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    private Map<String, String> getCategoryFeatures(Product.Category category) {
        Map<String, String> features = new HashMap<>();
        
        switch(category) {
            case TOYS:
                features.put("Material", "Non-toxic, durable materials");
                features.put("Age Suitability", "All ages");
                features.put("Washable", "Machine washable");
                features.put("Safety", "Choking hazard tested");
                features.put("Warranty", "6 months");
                break;
            case FOOD:
                features.put("Ingredients", "Premium natural ingredients");
                features.put("Nutrition", "Complete & balanced");
                features.put("Preservatives", "No artificial preservatives");
                features.put("Age Group", "Adult pets");
                features.put("Storage", "Store in cool, dry place");
                break;
            case ACCESSORIES:
                features.put("Material", "High-quality materials");
                features.put("Adjustable", "Multiple size settings");
                features.put("Weather Resistant", "All-weather use");
                features.put("Easy Clean", "Wipe clean surface");
                features.put("Warranty", "1 year limited");
                break;
            case GROOMING:
                features.put("Tools Included", "Multiple grooming tools");
                features.put("Ergonomic", "Comfortable grip design");
                features.put("Materials", "Stainless steel & plastic");
                features.put("Pet Size", "Suitable for all sizes");
                features.put("Storage", "Includes carrying case");
                break;
            case BEDS:
                features.put("Filling", "Memory foam comfort");
                features.put("Cover", "Removable, washable cover");
                features.put("Non-slip", "Anti-slip bottom");
                features.put("Hypoallergenic", "Allergy-friendly materials");
                features.put("Warranty", "2 years");
                break;
            case COLLARS:
                features.put("Material", "Genuine leather/nylon");
                features.put("Hardware", "Rust-resistant buckles");
                features.put("Adjustable", "Multiple hole settings");
                features.put("D-ring", "Strong leash attachment");
                features.put("Comfort", "Padded interior");
                break;
            default:
                features.put("Quality", "Premium construction");
                features.put("Durability", "Long-lasting materials");
                features.put("Safety", "Pet-safe design");
                features.put("Easy Use", "User-friendly");
                features.put("Support", "Customer service included");
        }
        
        return features;
    }
}
