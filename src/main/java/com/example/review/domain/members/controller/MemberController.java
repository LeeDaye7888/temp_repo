package com.example.review.domain.members.controller;

import com.example.review.domain.members.dto.MemberResponse;
import com.example.review.domain.members.dto.UpdateMemberPaswordRequest;
import com.example.review.domain.members.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Tag(name = "멤버 관련 api", description = "회원 정보 조회, 삭제, 수정 api들입니다.")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회원 상세 조회 api", description = "사용자가 자신의 정보를 조회하는 api 입니다.")
    public MemberResponse getOneMember(@AuthenticationPrincipal User user) {
        return memberService.getOne(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "회원 탈퇴 api", description = "일반 사용자가 탈퇴하는 api 입니다.")
    public void deleteUser(@AuthenticationPrincipal User user) {
        memberService.deleteUser(user);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "회원 비밀번호 변경 api", description = "사용자가 자신의 비밀번호를 변경하는 api 입니다.")
    public void updateMemberPassword(@AuthenticationPrincipal User user,
        @Valid @RequestBody UpdateMemberPaswordRequest request) {
        memberService.updatePassword(user, request);
    }
}
