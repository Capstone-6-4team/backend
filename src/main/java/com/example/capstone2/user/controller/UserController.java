package com.example.capstone2.user.controller;

import com.example.capstone2.common.response.ApiResponse;
import com.example.capstone2.user.dto.RegisterRequest;
import com.example.capstone2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest registerRequest) {
        userService.create(registerRequest);
        return ApiResponse.success();
    }
}
