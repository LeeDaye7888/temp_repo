package com.example.review.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex, HttpServletRequest request) {
        log.error("Request: %s , Message: %s".formatted(request.getRequestURI(),
            ex.getLocalizedMessage()), ex);
        return ResponseEntity.internalServerError().body("서버 내부 오류가 발생했습니다.");
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex,
        HttpServletRequest request) {
        log.error("Request: {}, Message: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(ex.getErrorCode().getStatus())
            .body(ex.getErrorCode().getMessage());
    }
}

