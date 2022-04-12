package com.example.capstone2.user.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.dto.RegisterRequest;
import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
public class User extends BaseEntity{

    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(value = EnumType.ORDINAL)
    private UserType userType;

    @PositiveOrZero
    @NotNull
    private Long point;

    public static User from(RegisterRequest registerRequest) {
        User user = new User();
        user.email = registerRequest.getEmail();
        user.password = registerRequest.getPassword();
        user.name = registerRequest.getName();
        user.userType = registerRequest.getUserType();
        user.point = 0l;

        return user;
    }
}
