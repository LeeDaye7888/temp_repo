package com.example.review.global.security;

import com.example.review.global.exception.ErrorCode;
import com.example.review.global.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponse error = new ErrorResponse(ErrorCode.FORBIDDEN_ERROR.getStatus(), ErrorCode.FORBIDDEN_ERROR.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ErrorCode.FORBIDDEN_ERROR.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));

        // throw new BusinessException(ErrorCode.FORBIDDEN_ERROR); -> @RestControllerAdvice와 @ControllerAdvice는 Spring의 영역이다. Spring Security는 Spring 이전에 필터링 한다. 즉 Filter 단에서 발생한 예외를 핸들링 해주지 못한다.
    }
}
