package com.example.capstone2.user.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.dto.RegisterRequest;
import com.example.capstone2.user.entity.infodetails.AvailableLanguage;
import com.example.capstone2.user.entity.infodetails.UserCharacteristic;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Convert(converter = UserType.Converter.class)
    private UserType userType;

    @Embedded
    private UserCharacteristic characteristic;

    @PositiveOrZero
    @NotNull
    private Long point;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableLanguage> availableLanguages = new ArrayList<>();

    public static User from(RegisterRequest registerRequest) {
        User user = new User();
        user.email = registerRequest.getEmail();
        user.password = registerRequest.getPassword();
        user.name = registerRequest.getName();
        user.userType = registerRequest.getUserType();
        user.point = 0l;
        user.characteristic = registerRequest.getCharacteristic();
        user.availableLanguages.addAll(
                registerRequest.getAvailableLanguages()
                        .stream()
                        .map(l -> AvailableLanguage.of(l, user))
                        .collect(Collectors.toList()));

        return user;
    }
}
