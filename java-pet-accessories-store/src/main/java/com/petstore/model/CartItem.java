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
        String rob;
        rob = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL1BST0QtUE9DLmF1dGguc2VhbGlnaHRzLmlvLyIsImp3dGlkIjoiUFJPRC1QT0MsbmVlZFRvUmVtb3ZlLEFQSUdXLTZmNjQ2ZTMwLTNlZTItNGEzYS04NGIwLTRhZDJjNWJmMGU0NywxNzUwOTM1OTE1NjE2Iiwic3ViamVjdCI6InRyaWNlbnRpcy1kZW1vQGFnZW50IiwiYXVkaWVuY2UiOlsiYWdlbnRzIl0sIngtc2wtcm9sZSI6ImFnZW50IiwieC1zbC1zZXJ2ZXIiOiJodHRwczovL3RyaWNlbnRpcy1kZW1vLnNlYWxpZ2h0cy5jby9hcGkiLCJzbF9pbXBlcl9zdWJqZWN0IjoiIiwiaWF0IjoxNzUwOTM1OTE1fQ.URZJypAuwdabLvXWsMmtzcxBbwmHnPlwofnTt_Dved9J5P43H7c3D2_vfEyBaYoflMCovaTOXpuQOMDDSSEHGnZsLjQWTithbtpmm7jVLE7YVWPnbAUDTrU_16ba2RC0jknsWw2cvSe18AxDI9yHu_ojlDIXHcaJNNxO2S2ZxgRWQS9LyJ91UCgrlYKBjFsWNwnRPtEPVcioabNZua7YiyUMp-nYkizY-xDoI225fFfV3Ew1arXQobGT3hLLj9YKSNn5Yb1F95LVzWD70Mpbo1eF3ut1fptJFrmTSEFy68EuJerJT5B9INcd0rT7EidO6zpeUJaN382Yn9qCUjBHu0-a-gwUnALozJje4R7LqfFtaPBuBV52F5GnoE-lIf-_Pia1qK2kQcaRjy_Mm1TkGndtgMvxiS5to_S3KvNPUm9ESc58UF72fRb2VjESOs0qiV2MkyB0jBpZ-gRZ4yjMRq9B_Eys7mgdXaXI5HM9sB7mydq2xwXix6OqgHG9ptr-Oivd9xLbYh8rRTIznjnUOOvWc7qJDWg9b4oFDbWVYF066Cnn-rbspvd7CF_3vebzvuU0CRW_JPHqpm2lyODVxhWLEsinc-rNHAme5JkZW8rfZgj3mWx6iZQ9eEsnuT4nLlAhYzJh6RkU5Ml2H7em6ZR0nDEArDW79-F_oQkbAJc";
        
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