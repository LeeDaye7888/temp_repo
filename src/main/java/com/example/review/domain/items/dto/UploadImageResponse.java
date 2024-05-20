package com.example.review.domain.items.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "상품 이미지 업로드 응답 DTO")
public record UploadImageResponse(
    @Schema(description = "상품 이미지 id 리스트", example = "[1, 2, 3]")
    List<Long> imgIds

) {

}


