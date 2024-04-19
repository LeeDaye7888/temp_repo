package com.example.review.domain.items.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "카테고리 요청 DTO")
public record CategoryRequest(
        @NotBlank
        @Schema(description = "카테고리명", example = "전자제품")
        String categoryName
) {
}
