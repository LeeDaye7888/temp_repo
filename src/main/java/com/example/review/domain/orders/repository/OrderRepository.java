package com.example.review.domain.orders.repository;

import com.example.review.domain.members.entity.Member;
import com.example.review.domain.orders.entity.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByMerchantId(String merchantId);
    List<Order> findAllByMember(Member member);
    Page<Order> findAllByMember(Member member, Pageable pageable);

    Optional<Order> findByMerchantId(UUID merchantId);
}
