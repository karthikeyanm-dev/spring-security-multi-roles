package com.karthi.securityiitest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_TEACHER(1), // Lowest
    ROLE_HEAD(2),
    ROLE_ADMIN(3),
    ROLE_SUPER_ADMIN(4); // highest

    private final int id;
}
