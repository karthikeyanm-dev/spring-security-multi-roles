package com.karthi.securityiitest.dto;

import com.karthi.securityiitest.model.Role;

public record RegisterRequest(
        String username,
        String password,
        Role role
)
{}
