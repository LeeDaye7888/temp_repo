package com.example.review.domain.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateAccessTokenRequest(
        @NotBlank
        @Schema(description = "사용자의 리프레시 토큰", example = "hfdhfskjhfskjhdkjshkdj")
        String refreshToken
) {
}
