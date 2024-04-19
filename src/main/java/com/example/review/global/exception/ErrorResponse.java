package com.example.review.global.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus status,
        String message
) {
}
