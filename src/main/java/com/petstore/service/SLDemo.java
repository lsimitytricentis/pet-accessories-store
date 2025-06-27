package com.petstore.service;

import com.petstore.model.Product;
import com.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SLDemo {
    
      
    public static int Toy() {
        System.out.println("======================================================================================\n\n");
        System.out.println("SL Toy: This is covered by a Tosca Test and two System Tests. Last change 11:23");
        System.out.println("======================================================================================\n\n");
        return 0;
    }
    public static int Food() {
        System.out.println("======================================================================================\n\n");
        System.out.println("SL Food: This is covered by a Tosca Test. Last change 14:21.");
        System.out.println("======================================================================================\n\n");
        return 0;
    }
    public static int Accesssoires() {
        System.out.println("======================================================================================\n\n");
        System.out.println("SL Accesssoires: This is covered by a Manual Test. Last change 11:23.");
        System.out.println("======================================================================================\n\n");
        return 0;
    }
    public static int Grooming() {
        System.out.println("======================================================================================\n\n");
        System.out.println("SL Grooming: This is not covered by a Test at all. Last change 11:24.");
        System.out.println("======================================================================================\n\n");
        return 0;
    }
    public static int Bed() {
        System.out.println("======================================================================================\n\n");
        System.out.println("SL Bed: This is covered by a Manual Test. Last change 14:21.\n");
        System.out.println("======================================================================================\n\n");
        return 0;
    }
}
    
