package com.karthi.securityiitest.controller;

import com.karthi.securityiitest.dto.AuthResponse;
import com.karthi.securityiitest.dto.CreateUserRequest;
import com.karthi.securityiitest.dto.RegisterRequest;
import com.karthi.securityiitest.model.Role;
import com.karthi.securityiitest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    final private AuthService authService;

    @PostMapping("/create-head")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> createHead(@RequestBody CreateUserRequest request, Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().toString();
        String createdBy = authentication.getName() + " - " +role;
        return ResponseEntity.ok(authService.createUser(request, Role.ROLE_HEAD, createdBy));
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Admin Dashboard");
    }

}
