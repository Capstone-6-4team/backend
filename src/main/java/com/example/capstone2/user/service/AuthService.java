package com.example.capstone2.user.service;

import com.example.capstone2.common.jwt.JwtService;
import com.example.capstone2.common.response.ApiResponse;
import com.example.capstone2.user.dto.LoginRequest;
import com.example.capstone2.user.dto.LoginResponse;
import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String token = jwtService.generateToken(authenticate);
        User user = userService.findByEmail(authenticate.getName());
        return new LoginResponse(token, user.getId());
    }
}
