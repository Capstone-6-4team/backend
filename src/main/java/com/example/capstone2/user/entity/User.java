package com.example.capstone2.user.entity;

import com.example.capstone2.common.entity.BaseEntity;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class User extends BaseEntity {

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


}
