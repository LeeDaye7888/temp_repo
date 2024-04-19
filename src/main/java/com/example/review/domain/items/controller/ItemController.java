package com.example.review.domain.items.controller;

import com.example.review.domain.items.dto.ItemPageResponse;
import com.example.review.domain.items.dto.ItemResponse;
import com.example.review.domain.items.service.implement.ItemServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "상품 관련 api", description = "전체 사용자가 볼 수 있는 상품 조회 api 입니다.")
public class ItemController {

    private final ItemServiceImpl itemService;

    //상품 단건 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/items/{itemId}")
    @Operation(summary = "상품 상세 조회 api", description = "상품을 단건 조회하는 api 입니다.")
    public ItemResponse getItemDetail(@PathVariable Long itemId) {
        return itemService.getOne(itemId);
    }

    // 상품 전체 조회
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/items")
    @Operation(summary = "상품 전체 조회 api", description = "특정 카테고리에 속하는 모든 상품을 조회하는 api 입니다.")
    public ItemPageResponse getAllItems(Pageable pageable,
                                        @RequestParam Long categoryId) {
        return itemService.getAll(pageable, categoryId);
    }

}