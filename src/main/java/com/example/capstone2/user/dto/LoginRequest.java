package com.example.capstone2.user.dto;

import com.example.capstone2.user.entity.UserType;
import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
    private UserType userType;
}
