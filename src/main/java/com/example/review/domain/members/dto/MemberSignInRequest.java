package com.example.review.domain.members.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청 DTO")
public record MemberSignInRequest(
        @NotBlank
        @Schema(description = "사용자 이메일", example = "amy@naver.com")
        String email,

        @NotBlank
        @Schema(description = "사용자 비밀번호", example = "Amy1234!")
        String password
) {
}
