package com.karthi.securityiitest.service;

import com.karthi.securityiitest.dto.*;
import com.karthi.securityiitest.model.Role;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse createUser(RegisterRequest request, Role role,String createdBy); // Create Any user Only For SuperAdmin
    AuthResponse createUser(CreateUserRequest request,Role role,String createdBy); // Create For specific roles
}
