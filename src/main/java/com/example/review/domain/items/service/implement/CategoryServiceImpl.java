package com.example.review.domain.items.service.implement;

import com.example.review.domain.items.dto.CategoryRequest;
import com.example.review.domain.items.dto.CategoryResponse;
import com.example.review.domain.items.entity.Category;
import com.example.review.domain.items.repository.CategoryRepository;
import com.example.review.domain.items.repository.ItemRepository;
import com.example.review.domain.items.service.CategoryService;
import com.example.review.global.exception.BusinessException;
import com.example.review.global.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
            .map(category -> {
                return new CategoryResponse(category.getCategoryId(), category.getCategoryName());
            })
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        Optional<Category> foundCategory = categoryRepository.findByCategoryName(
            request.categoryName());
        if (foundCategory.isPresent()) {
            throw new BusinessException(ErrorCode.CATEGORY_EXIST_GOODS);
        }

        Category savedCategory = categoryRepository.save(Category.builder()
            .categoryName(request.categoryName())
            .build());
        return new CategoryResponse(savedCategory.getCategoryId(), savedCategory.getCategoryName());
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));

        if (itemRepository.findFirstByCategory(category).isPresent()) {
            throw new BusinessException(ErrorCode.CATEGORY_EXIST_GOODS);
        }

        categoryRepository.deleteById(categoryId);
    }

    @Override
    @Transactional
    public void update(CategoryRequest request, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));
        category.updateCategory(request);
    }
}