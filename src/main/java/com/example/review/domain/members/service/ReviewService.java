package com.example.review.domain.members.service;


import com.example.review.domain.members.dto.ReviewPageResponse;
import com.example.review.domain.members.dto.ReviewRequest;
import com.example.review.domain.members.dto.ReviewResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

public interface ReviewService {
    ReviewResponse create(ReviewRequest request, User user);
    void update(Long reviewId, ReviewRequest reviewRequest, User user);
    void delete(Long reviewId, User user);
    ReviewPageResponse getAllByMember(User user, Pageable pageable);
    ReviewPageResponse getAllByItem(Long itemId, Pageable pageable);
}
