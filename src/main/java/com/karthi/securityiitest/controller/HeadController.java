package com.karthi.securityiitest.controller;

import com.karthi.securityiitest.dto.AuthResponse;
import com.karthi.securityiitest.dto.CreateUserRequest;
import com.karthi.securityiitest.model.Role;
import com.karthi.securityiitest.model.User;
import com.karthi.securityiitest.service.AuthService;
import com.karthi.securityiitest.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/head")
@RequiredArgsConstructor
public class HeadController {
    private final AuthService authService;
    private final UserServiceImpl userService;

    @PostMapping("/create-teacher")
    @PreAuthorize("hasRole('HEAD')")
    public ResponseEntity<AuthResponse> createTeacher(@RequestBody CreateUserRequest createUserRequest, Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().toString();
        String createdBy = authentication.getName()  + " - " + role;
        return new ResponseEntity<>(authService.createUser(createUserRequest, Role.ROLE_TEACHER, createdBy), HttpStatus.CREATED);
    }

    @GetMapping("/my-teachers")
    @PreAuthorize("hasRole('HEAD')")
    public ResponseEntity<List<User>> getMyTeachers(Authentication authentication) {
        List<User> teachers = userService.findAllUsersByRole(Role.ROLE_TEACHER);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

}
