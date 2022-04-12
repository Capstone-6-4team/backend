package com.example.capstone2.user.entity;

import com.example.capstone2.common.entityenum.EntityEnum;
import com.example.capstone2.common.entityenum.EntityEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType implements EntityEnum {
    ROLE_GUEST("GUEST", "G"),
    ROLE_HOST("HOST", "H");

    private String name;
    private String code;

    @javax.persistence.Converter
    public static class Converter extends EntityEnumConverter<UserType> {
        public Converter() {
            super(UserType.class);
        }
    }
}