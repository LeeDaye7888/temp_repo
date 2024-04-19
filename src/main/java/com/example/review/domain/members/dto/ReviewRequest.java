package com.example.review.domain.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "리뷰 요청 DTO")
public record ReviewRequest(
        @NotBlank
        @Schema(description = "리뷰 제목", example = "상품1에 대한 리뷰")
        String title,

        @NotBlank
        @Schema(description = "리뷰 내용", example = "이 제품 너무 좋아요.")
        String content,

        @NotNull
        @Min(value = 1)
        @Max(value = 5)
        @Schema(description = "리뷰 별점", example = "3")
        int star,

        @NotNull
        @Schema(description = "주문 상품 ID", example = "1")
        Long OrderItemId
) {
}