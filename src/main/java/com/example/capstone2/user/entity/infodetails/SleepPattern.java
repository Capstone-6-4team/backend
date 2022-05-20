package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entityenum.EntityEnum;
import com.example.capstone2.common.entityenum.EntityEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SleepPattern implements EntityEnum {
    MORNING("morning", "M"),
    EVENING("evening", "E");

    private String name;
    private String code;

    @javax.persistence.Converter
    public static class Converter extends EntityEnumConverter<SleepPattern> {
        public Converter() {
            super(SleepPattern.class);
        }
    }
}
