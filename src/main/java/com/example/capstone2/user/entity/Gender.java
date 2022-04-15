package com.example.capstone2.user.entity;

import com.example.capstone2.common.entityenum.EntityEnum;
import com.example.capstone2.common.entityenum.EntityEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Converter;

@AllArgsConstructor
@Getter
public enum Gender implements EntityEnum {
    MALE("남자", "M"),
    FEMALE("여자", "F");

    String name;
    String code;

    @javax.persistence.Converter
    public static class Converter extends EntityEnumConverter<Gender> {
        public Converter() {
            super(Gender.class);
        }
    }
}
