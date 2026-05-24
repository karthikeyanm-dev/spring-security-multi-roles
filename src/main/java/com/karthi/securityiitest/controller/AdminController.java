package com.karthi.securityiitest.controller;

import com.karthi.securityiitest.dto.AuthResponse;
import com.karthi.securityiitest.dto.CreateUserRequest;
import com.karthi.securityiitest.dto.RegisterRequest;
import com.karthi.securityiitest.model.Role;
import com.karthi.securityiitest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    final private AuthService authService;

    @PostMapping("/create-head")
    public ResponseEntity<AuthResponse> createHead(@RequestBody CreateUserRequest request, Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().toString();
        String createdBy = authentication.getName() + " - " +role;
        return ResponseEntity.ok(authService.createUser(request, Role.ROLE_HEAD, createdBy));
    }

}
