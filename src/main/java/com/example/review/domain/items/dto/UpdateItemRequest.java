package com.example.review.domain.items.dto;

import com.example.review.domain.items.entity.ItemState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.lang.Nullable;

@Schema(description = "상품 수정 요청 DTO")
public record UpdateItemRequest(

        @NotBlank
        @Schema(description = "상품 이름", example = "노트북")
        @Size(min = 2, max = 50)
        String itemName,

        @NotNull
        @Schema(description = "카테고리 id", example = "1")
        Long categoryId,

        @Min(value = 1)
        @NotNull
        @Schema(description = "상품 가격", example = "879000")
        int price,

        @NotNull
        @Schema(description = "상품 수량", example = "1000")
        int count,

        @Nullable
        @Schema(description = "상품 옵션", example = "{색상: WHITE}")
        List<Option> optionValue,

        @NotNull
        @Schema(description = "상품 판매 상태", example = "품절")
        ItemState itemState,

        @Nullable
        @Schema(description = "상품 상세 설명", example = "가볍고 화질이 선명해요.")
        String description


)  {
    public record Option (
            String key,
            String value) {
    }

}
