package com.karthi.securityiitest.service;

import com.karthi.securityiitest.dto.*;
import com.karthi.securityiitest.model.Role;
import com.karthi.securityiitest.model.User;
import com.karthi.securityiitest.repo.UserRepo;
import com.karthi.securityiitest.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    final private UserRepo userRepo;
    final private PasswordEncoder passwordEncoder;
    final private AuthenticationProvider authenticationProvider;
    final private AuthenticationManager authenticationManager;
    final private UserDetailsService  userDetailsService;
    final private JwtService jwtService;


    @Override
    public AuthResponse login(LoginRequest request) {
        // ── Step 1: Authenticate (verifies username + password) ──
        // throws exception automatically if wrong credentials

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        // ── Step 2: If we reach here, credentials are correct ──
        // Load user and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        Map<String , Object> extraClaims = new HashMap<>();
        extraClaims.put("role", userDetails.getAuthorities());

        String token = jwtService.generateToken(extraClaims, userDetails);
        return new AuthResponse(token,request.username(),"Login Successfully",userDetails.getAuthorities().toString());
    }

    @Override
    public AuthResponse createUser(RegisterRequest request,Role role,String createdBy) {
        if (userRepo.existsByUsername(request.username())) {
            throw new RuntimeException("Username already taken");
        }
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(role)               // ← decided by caller, not request body
                .enabled(true)
                .createdBy(createdBy)     // ← who created this account
                .build();
        userRepo.save(user);
        return new AuthResponse(
                null,
                user.getUsername(),
                user.getRole().name(),
                user.getRole().name() + " created successfully by " + createdBy
        );

    }

    @Override
    public AuthResponse createUser(CreateUserRequest request,Role role,String createdBy) {
        if (userRepo.existsByUsername(request.username())) {
            throw new RuntimeException("Username already taken");
        }
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .enabled(true)
                .createdBy(createdBy)
                .build();
        userRepo.save(user);
        return new AuthResponse(
                null,
                user.getUsername(),
                user.getRole().name(),
                user.getRole().name() + " created successfully by " + createdBy
        );
    }


}
