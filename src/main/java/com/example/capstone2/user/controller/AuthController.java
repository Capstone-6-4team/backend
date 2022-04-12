package com.example.capstone2.user.controller;

import com.example.capstone2.common.response.ApiResponse;
import com.example.capstone2.user.dto.LoginRequest;
import com.example.capstone2.user.dto.LoginResponse;
import com.example.capstone2.user.entity.UserType;
import com.example.capstone2.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return loginResponse;
    }


}
