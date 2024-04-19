package com.example.review.domain.items.service;

import com.example.review.domain.items.dto.*;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

public interface ItemService {
    CreateItemResponse create(ItemRequest itemRequest, List<MultipartFile> multipartFiles, User user);
    UpdateItemResponse update(Long itemId, UpdateItemRequest itemRequest, List<MultipartFile> multipartFiles, User user);
    void delete(Long itemId, User user);
    SellerItemsResponse getSellerAll(Pageable pageable, User user);
    ItemResponse getOne(Long itemId);
    ItemPageResponse getAll(Pageable pageable, Long categoryId);
}
