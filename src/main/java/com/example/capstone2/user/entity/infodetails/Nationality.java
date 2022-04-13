package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entityenum.EntityEnum;
import com.example.capstone2.common.entityenum.EntityEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Converter;

@Getter
@AllArgsConstructor
public enum Nationality implements EntityEnum {
    KR("KOREA", "kr"),
    US("UNITED_STATE", "us");

    String name;
    String code;

    @javax.persistence.Converter
    public static class Converter extends EntityEnumConverter<Nationality> {
        public Converter() {
            super(Nationality.class);
        }
    }
}
