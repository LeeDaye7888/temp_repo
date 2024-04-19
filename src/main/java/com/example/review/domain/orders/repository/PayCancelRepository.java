package com.example.review.domain.orders.repository;

import com.example.review.domain.orders.entity.PayCancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayCancelRepository extends JpaRepository<PayCancel, Long> {
}
