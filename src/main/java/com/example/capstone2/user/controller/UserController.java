package com.example.capstone2.user.controller;

import com.example.capstone2.user.dto.RegisterRequest;
import com.example.capstone2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if(userService.isExistEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("이미 가입된 email 입니다.");
        }

        userService.create(registerRequest);
        return ResponseEntity.ok().build();
    }
}
