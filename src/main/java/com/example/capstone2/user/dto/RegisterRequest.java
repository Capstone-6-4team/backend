package com.example.capstone2.user.dto;

import com.example.capstone2.user.entity.UserType;
import com.example.capstone2.user.entity.infodetails.Language;
import com.example.capstone2.user.entity.infodetails.UserCharacteristic;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class RegisterRequest {
    private String email;
    @Setter
    private String password;
    private String name;
    private UserType userType;
    private UserCharacteristic characteristic;
    private List<Language> availableLanguages;
}
