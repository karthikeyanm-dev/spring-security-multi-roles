package com.karthi.securityiitest.controller;

import com.karthi.securityiitest.dto.AuthResponse;
import com.karthi.securityiitest.dto.LoginRequest;
import com.karthi.securityiitest.dto.RegisterRequest;
import com.karthi.securityiitest.dto.RegisterResponse;
import com.karthi.securityiitest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
