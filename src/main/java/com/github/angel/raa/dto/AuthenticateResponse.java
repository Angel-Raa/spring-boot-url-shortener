package com.github.angel.raa.dto;

public record AuthenticateResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {
}
