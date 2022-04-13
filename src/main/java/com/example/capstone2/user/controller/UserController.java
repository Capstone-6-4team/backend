package com.example.capstone2.user.controller;

import com.example.capstone2.common.response.ApiResponse;
import com.example.capstone2.common.response.ResultCode;
import com.example.capstone2.user.dto.RegisterRequest;
import com.example.capstone2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest registerRequest) {
        if(userService.isExistEmail(registerRequest.getEmail())) {
            return ApiResponse.failure(ResultCode.BAD_REQUEST, "이미 가입된 email 입니다.");
        }

        userService.create(registerRequest);
        return ApiResponse.success();
    }
}
