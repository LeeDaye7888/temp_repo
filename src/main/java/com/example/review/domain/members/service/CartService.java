package com.example.review.domain.members.service;

import com.example.review.domain.members.dto.*;
import com.example.review.domain.members.entity.Member;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

public interface CartService {
    CartResponse create(CreateCartRequest cartRequest, Member member);
    void update(Long cartId, UpdateCartRequest cartRequest, Member member);
    CartPageResponse getAll(Pageable pageable, Member member);
    void deleteSelectedCarts(List<Long> cartIds, Member member);
}
