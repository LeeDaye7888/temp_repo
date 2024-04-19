package com.example.review.domain.members.controller;

import com.example.review.domain.members.dto.ReviewPageResponse;
import com.example.review.domain.members.dto.ReviewRequest;
import com.example.review.domain.members.dto.ReviewResponse;
import com.example.review.domain.members.service.implement.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "리뷰 관련 api", description = "리뷰 관련 api입니다.")
public class ReviewController {

    private final ReviewServiceImpl reviewService;


    /*리뷰 작성하기*/
    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "리뷰 등록 api", description = "리뷰를 등록하는 api 입니다.")
    public ReviewResponse addReview(@Valid @RequestBody ReviewRequest request,
                                    @AuthenticationPrincipal User user) {
        return reviewService.create(request, user);
    }

    /* 리뷰 수정하기 */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정 api", description = "리뷰를 수정하는 api 입니다.")
    public void updateReview(@PathVariable Long reviewId,
                             @Valid @RequestBody ReviewRequest reviewRequest,
                             @AuthenticationPrincipal User user) {
        reviewService.update(reviewId, reviewRequest, user);
    }

    /* 리뷰 삭제하기 */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제 api", description = "리뷰를 삭제하는 api 입니다.")
    public void deleteReview(@PathVariable Long reviewId,
                             @AuthenticationPrincipal User user) {
        reviewService.delete(reviewId, user);
    }

    /* 리뷰 전체 조회 (마이페이지) */
    @GetMapping("/members/reviews")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "리뷰 전체 조회 api", description = "마이페이지에서 리뷰를 전체 조회하는 api 입니다.")
    public ReviewPageResponse findAllByMember(@AuthenticationPrincipal User user,
                                              Pageable pageable) {
        return reviewService.getAllByMember(user, pageable);
        // 파라미터로 Pagable만 주면 PageRequest.of(0,20)으로 만들어진다. page=0, size(한페이지당크기)=20, 정렬의 기본은 오름차순,
        // 최근에 등록된 상품부터 보이게 하고 싶어서 내림차순으로 바꿈, 사이즈는 15로 통일
        // @PageableDefault(size = 15, sort = "reviewId", direction = Sort.Direction.DESC)
        // -> application.yml에서 페이징 default를 통일함 -> 이렇게 하면 매번 @PageDefault 써서 페이징 디폴트 설정 안해줘도 됨

        // pagable을 파라미터로 주면 알아서 page, size, sort등을 줄 수 있음
    }

    /* 리뷰 전체 조회 (아이템 상세 페이지) */
    @GetMapping("/items/{itemId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "리뷰 전체 조회 api", description = "아이템 상세 페이지에서 리뷰를 전체 조회하는 api 입니다.")
    public ReviewPageResponse findAllByItem(@PathVariable Long itemId,
                                            Pageable pageable) { // PageImpl: Spring Data에서 페이징된 데이터를 표현하기 위한 객체. Page 인터페이스 구현체
        return reviewService.getAllByItem(itemId, pageable);
    }
}