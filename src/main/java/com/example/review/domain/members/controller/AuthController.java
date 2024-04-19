package com.example.review.domain.members.controller;

import com.example.review.domain.members.dto.MemberSignInRequest;
import com.example.review.domain.members.dto.MemberSignInResponse;
import com.example.review.domain.members.dto.MemberSignUpRequest;
import com.example.review.domain.members.service.AuthService;
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
@Tag(name = "로그인, 회원가입 관련 api", description = "사용자가 회원가입, 로그인하는 api들입니다.")
public class AuthController {

    private final AuthService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원가입 api", description = "사용자가 회원가입하는 api 입니다.")
    public void saveMember(@RequestBody @Valid MemberSignUpRequest request) {
        memberService.saveMember(request);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "로그인 api", description = "사용자가 로그인하는 api 입니다.")
    public MemberSignInResponse signInMember(@RequestBody @Valid MemberSignInRequest request) {
        return memberService.signIn(request);
    }
}
