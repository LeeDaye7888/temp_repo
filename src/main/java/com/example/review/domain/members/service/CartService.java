package com.example.review.domain.members.service;

import com.example.review.domain.members.dto.*;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

public interface CartService {
    CartResponse create(CreateCartRequest cartRequest, User user);
    void update(Long cartId, UpdateCartRequest cartRequest, User user);
    CartPageResponse getAll(Pageable pageable, User user);
    void deleteSelectedCarts(List<Long> cartIds, User user);
}
