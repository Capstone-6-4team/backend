package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entityenum.EntityEnum;
import com.example.capstone2.common.entityenum.EntityEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Language implements EntityEnum {
    KOREAN("한국어", "ko"),
    ENGLISH("영어", "en");

    private String name;
    private String code;

    @javax.persistence.Converter
    public static class Converter extends EntityEnumConverter<Language> {
        public Converter() {
            super(Language.class);
        }
    }
}
