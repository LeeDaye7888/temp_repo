package com.example.review.domain.members.controller;

import com.example.review.domain.members.dto.CreateAccessTokenReponse;
import com.example.review.domain.members.dto.CreateAccessTokenRequest;
import com.example.review.domain.members.service.implement.TokenServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "토큰 관련 api", description = "토큰 관련 api입니다.")
public class TokenController {

    private final TokenServiceImpl tokenService;

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary ="엑세스 토큰 재발급 api", description = "리프레시 토큰으로 새 엑세스 토큰 발급해주는 api입니다.")
    public CreateAccessTokenReponse createNewAccessToken(@RequestBody @Valid CreateAccessTokenRequest request) {
        return tokenService.createNewAccessToken(request);
    }
}
