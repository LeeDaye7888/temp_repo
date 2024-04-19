package com.example.review.global.security;

import com.example.review.global.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String accessToken = getAccessToken(authorizationHeader);

        try {
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);// 인증 객체 가져와서 SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", ErrorCode.EXPIRED_JWT_ERROR.getMessage());
        } catch (MalformedJwtException | SignatureException e) {
            request.setAttribute("exception", ErrorCode.INVALID_JWT_ERROR.getMessage());
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", ErrorCode.UNSUPPORTED_JWT_TOKEN.getMessage());
        } catch (IllegalArgumentException e) { // JWT 클레임 문자열이 비어있는 경우
            request.setAttribute("exception", ErrorCode.TOKEN_CLAIM_EMPTY.getMessage());
        } catch (AuthenticationException | NullPointerException e) {
            request.setAttribute("exception", ErrorCode.USER_AUTH_ERROR.getMessage());
        }
        filterChain.doFilter(request, response); //doFilter()를 호출하여 다음 필터로 요청을 전달
        // 이거 넘어가고 나서 인증 실패 필터가 등장함
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
