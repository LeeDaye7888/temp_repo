package com.example.review.domain.orders.repository;

import com.example.review.domain.orders.entity.Order;
import com.example.review.domain.orders.entity.Pay;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay, Long> {
    Optional<Pay> findByOrder(Order order);
}
