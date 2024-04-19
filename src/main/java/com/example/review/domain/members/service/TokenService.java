package com.example.review.domain.members.service;

import com.example.review.domain.members.dto.CreateAccessTokenReponse;
import com.example.review.domain.members.dto.CreateAccessTokenRequest;

public interface TokenService {
    CreateAccessTokenReponse createNewAccessToken(CreateAccessTokenRequest request);
}
