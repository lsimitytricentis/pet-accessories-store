package com.petstore.repository;

import com.petstore.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Product dogToy;
    private Product catFood;
    private Product dogCollar;

    @BeforeEach
    void setUp() {
        dogToy = new Product("Dog Toy", "Fun toy for dogs", 
            new BigDecimal("15.99"), "http://example.com/dog-toy.jpg", 
            Product.Category.TOYS, 10);

        catFood = new Product("Cat Food", "Premium cat food", 
            new BigDecimal("29.99"), "http://example.com/cat-food.jpg", 
            Product.Category.FOOD, 5);

        dogCollar = new Product("Dog Collar", "Leather dog collar", 
            new BigDecimal("19.99"), "http://example.com/dog-collar.jpg", 
            Product.Category.COLLARS, 8);

        entityManager.persistAndFlush(dogToy);
        entityManager.persistAndFlush(catFood);
        entityManager.persistAndFlush(dogCollar);
    }

    @Test
    void testFindByCategory() {
        List<Product> toys = productRepository.findByCategory(Product.Category.TOYS);
        assertEquals(1, toys.size());
        assertEquals("Dog Toy", toys.get(0).getName());

        List<Product> food = productRepository.findByCategory(Product.Category.FOOD);
        assertEquals(1, food.size());
        assertEquals("Cat Food", food.get(0).getName());

        List<Product> accessories = productRepository.findByCategory(Product.Category.ACCESSORIES);
        assertTrue(accessories.isEmpty());
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        List<Product> results = productRepository.findByNameContainingIgnoreCase("dog");
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(p -> p.getName().equals("Dog Toy")));
        assertTrue(results.stream().anyMatch(p -> p.getName().equals("Dog Collar")));

        List<Product> catResults = productRepository.findByNameContainingIgnoreCase("CAT");
        assertEquals(1, catResults.size());
        assertEquals("Cat Food", catResults.get(0).getName());

        List<Product> noResults = productRepository.findByNameContainingIgnoreCase("bird");
        assertTrue(noResults.isEmpty());
    }

    @Test
    void testFindByNameContainingIgnoreCase_PartialMatch() {
        List<Product> results = productRepository.findByNameContainingIgnoreCase("toy");
        assertEquals(1, results.size());
        assertEquals("Dog Toy", results.get(0).getName());

        List<Product> foodResults = productRepository.findByNameContainingIgnoreCase("food");
        assertEquals(1, foodResults.size());
        assertEquals("Cat Food", foodResults.get(0).getName());
    }

    @Test
    void testFindAll() {
        List<Product> allProducts = productRepository.findAll();
        assertEquals(3, allProducts.size());
    }

    @Test
    void testSaveAndFindById() {
        Product newProduct = new Product("Bird Cage", "Large bird cage", 
            new BigDecimal("89.99"), "http://example.com/bird-cage.jpg", 
            Product.Category.ACCESSORIES, 3);

        Product saved = productRepository.save(newProduct);
        assertNotNull(saved.getId());

        Product found = productRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Bird Cage", found.getName());
        assertEquals(new BigDecimal("89.99"), found.getPrice());
    }
}