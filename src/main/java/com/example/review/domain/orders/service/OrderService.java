package com.example.review.domain.orders.service;

import com.example.review.domain.orders.dto.OrderKeyResponse;
import com.example.review.domain.orders.dto.OrderPageResponse;
import com.example.review.domain.orders.dto.OrderRequest;
import com.example.review.domain.orders.dto.OrderResponse;
import com.example.review.domain.orders.dto.PayCancelRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

public interface OrderService {

    OrderKeyResponse generateKey();
    OrderResponse create(OrderRequest orderRequest, User user);
    void payCancel(PayCancelRequest payCancelRequest, User user);
    OrderResponse getOne(User user, Long orderId);
    OrderPageResponse getAll(User user, Pageable pageable);
}
