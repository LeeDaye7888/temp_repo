package com.example.review.domain.items.service;

import com.example.review.domain.items.dto.*;
import com.example.review.domain.members.entity.Member;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

public interface ItemService {

    CreateItemResponse create(ItemRequest itemRequest, List<MultipartFile> multipartFiles,
        Member member);

    UpdateItemResponse update(Long itemId, UpdateItemRequest itemRequest,
        List<MultipartFile> multipartFiles, Member member);

    void delete(Long itemId, Member member);

    SellerItemsResponse getSellerAll(Pageable pageable, Member member);

    ItemResponse getOne(Long itemId);

    ItemPageResponse getAll(Pageable pageable, Long categoryId);
}
