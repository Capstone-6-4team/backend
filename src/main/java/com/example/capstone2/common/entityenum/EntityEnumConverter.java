package com.example.capstone2.common.entityenum;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class EntityEnumConverter<T extends EntityEnum> implements AttributeConverter<T, String> {

    private final Class<T> clazz;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return Arrays.stream(clazz.getEnumConstants())
                .filter(v -> StringUtils.equals(dbData, v.getCode()))
                .findAny()
                .orElseThrow(() -> new UnsupportedOperationException(String.format("[%s] 지원하지 않는 enum 형식입니다.", dbData)));
    }
}
