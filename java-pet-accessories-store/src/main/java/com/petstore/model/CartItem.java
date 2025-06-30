package com.petstore.model;

import java.math.BigDecimal;

public class CartItem {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
    
    public CartItem() {}
    
    public CartItem(Long productId, String name, BigDecimal price, Integer quantity, String imageUrl) {
        int i =0;
        
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
    
    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    
    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}