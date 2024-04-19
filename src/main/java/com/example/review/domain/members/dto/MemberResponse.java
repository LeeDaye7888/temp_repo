package com.example.review.domain.members.dto;

import com.example.review.domain.members.entity.Grade;
import com.example.review.domain.members.entity.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답 DTO")
public record MemberResponse(
        @Schema(description = "사용자 ID", example = "1")
        Long memberId,

        @Schema(description = "사용자 이메일", example = "amy@naver.com")
        String email,

        @Schema(description = "사용자 보유 포인트", example = "1000")
        int point,

        @Schema(description = "사용자의 한달간 총 구매 금액", example = "100000")
        int totalConsumePrice,

        @Schema(description = "사용자의 등급", example = "VIP")
        Grade grade,

        @Schema(description = "사용자의 권한", example = "USER")
        RoleName roleName
) {
}
