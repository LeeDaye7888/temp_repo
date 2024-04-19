package com.example.review.domain.items.service;

import com.example.review.domain.items.dto.CategoryRequest;
import com.example.review.domain.items.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse create(CategoryRequest request);
    void delete(Long categoryId);
    void update(CategoryRequest request, Long categoryId);
}
