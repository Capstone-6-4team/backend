package com.example.capstone2.user.entity;

import com.example.capstone2.common.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private UserType userType;

    @Column(nullable = false)
    private Long point;
}
