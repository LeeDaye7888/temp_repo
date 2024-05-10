package com.example.review.domain.items.controller;

import static com.example.review.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.example.review.domain.items.dto.*;
import com.example.review.domain.items.service.ItemService;
import com.example.review.domain.members.entity.Member;
import com.example.review.domain.members.repository.MemberRepository;
import com.example.review.global.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller")
@Tag(name = "(판매자 권한) 상품 관련 api", description = "판매자의 상품 등록, 수정, 조회, 삭제 api입니다.")
public class ItemSellerController {

    private final ItemService itemService;
    private final MemberRepository memberRepository;

    //(판매자)상품 등록
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/items")
    @Operation(summary = "상품 등록 api", description = "상품을 등록하는 api 입니다.")
    public CreateItemResponse addItem(
        @Valid @RequestPart(value = "itemRequest", required = false) ItemRequest itemRequest,
        @AuthenticationPrincipal User user) {
        Member member = getMember(user);
        return itemService.createItem(itemRequest, member);
    }

    //상품 이미지 업로드
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/items/{itemId}")
    @Operation(summary = "상품 이미지 업로드 api", description = "상품 이미지를 업로드하는 api 입니다.")
    public UploadImageResponse uploadImage(@PathVariable Long itemId,
        @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
        @AuthenticationPrincipal User user) {
        Member member = getMember(user);
        return itemService.uploadItemImage(itemId, multipartFiles, member);
    }

    //상품 수정
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/items/{itemId}")
    @Operation(summary = "상품 수정 api", description = "상품을 수정하는 api 입니다.")
    public UpdateItemResponse updateItem(@PathVariable Long itemId,
        @Valid @RequestPart(value = "itemRequest", required = false) UpdateItemRequest itemRequest,
        @RequestPart(value = "file", required = false) List<MultipartFile> multipartFiles,
        @AuthenticationPrincipal User user) {
        Member member = getMember(user);
        return itemService.update(itemId, itemRequest, multipartFiles, member);
    }

    //상품 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "상품 삭제 api", description = "상품을 삭제하는 api 입니다.")
    public void deleteItem(@PathVariable Long itemId, @AuthenticationPrincipal User user) {
        Member member = getMember(user);
        itemService.delete(itemId, member);
    }

    //상품 조회(판매자)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/items")
    @Operation(summary = "상품 판매자 조회 api", description = "판매자 자신이 등록한 상품을 조회하는 api 입니다.")
    public SellerItemsResponse getSellerItems(Pageable pageable,
        @AuthenticationPrincipal User user) {
        Member member = getMember(user);
        return itemService.getSellerAll(pageable, member);
    }

    private Member getMember(User user) {
        getMember(user);
        return memberRepository.findByEmail(user.getUsername())
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));
    }
}

