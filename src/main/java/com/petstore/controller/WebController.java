package com.petstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }
    
    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable Long id) {
        return "forward:/index.html";
    }
    
    @GetMapping("/checkout")
    public String checkout() {
        return "forward:/index.html";
    }
}