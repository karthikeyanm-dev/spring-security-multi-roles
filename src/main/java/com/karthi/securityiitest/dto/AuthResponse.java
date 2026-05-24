package com.karthi.securityiitest.dto;

public record AuthResponse(
        String token,
        String username,
        String role,
        String message
) {
}
