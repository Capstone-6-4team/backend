package com.example.capstone2.user.dto;

import com.example.capstone2.user.entity.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RegisterRequest {
    private String email;
    @Setter
    private String password;
    private String name;
    private UserType userType;
}
