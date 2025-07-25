package com.petstore.repository;

import com.petstore.model.Order;
import com.petstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    List<Order> findByUserEmailOrderByCreatedAtDesc(String email);
}