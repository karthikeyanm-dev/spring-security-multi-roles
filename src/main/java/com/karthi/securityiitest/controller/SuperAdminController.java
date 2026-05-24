package com.karthi.securityiitest.controller;

import com.karthi.securityiitest.dto.AuthResponse;
import com.karthi.securityiitest.dto.CreateUserRequest;
import com.karthi.securityiitest.dto.RegisterRequest;
import com.karthi.securityiitest.model.User;
import com.karthi.securityiitest.repo.UserRepo;
import com.karthi.securityiitest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {
    final private UserRepo repo;
    private final AuthService authService;

    // Can Create Any User..

    @PostMapping("/create-any-user")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AuthResponse> createAnyUser(@RequestBody RegisterRequest request, Authentication authentication) {
        String createdBy = authentication.getName();
        return  ResponseEntity.ok(
                authService.createUser(request,request.role(),createdBy)
        );
    }

    // SUPER_ADMIN can view all users
    @GetMapping("/users")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(repo.findAll());
    }

}
