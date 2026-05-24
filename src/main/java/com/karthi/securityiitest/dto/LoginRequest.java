package com.karthi.securityiitest.dto;

public record LoginRequest(
        String username,
        String password
) {
}
