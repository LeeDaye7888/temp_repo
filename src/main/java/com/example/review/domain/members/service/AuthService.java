package com.example.review.domain.members.service;

import com.example.review.domain.members.dto.MemberSignInRequest;
import com.example.review.domain.members.dto.MemberSignInResponse;
import com.example.review.domain.members.dto.MemberSignUpRequest;

public interface AuthService {
    void saveMember(MemberSignUpRequest request);
    MemberSignInResponse signIn(MemberSignInRequest request);
}
