package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.user.entity.Gender;
import lombok.Getter;

import javax.persistence.*;

@Embeddable
@Getter
public class UserCharacteristic {

    @Convert(converter = Nationality.Converter.class)
    private Nationality nationality;

    @Convert(converter = Gender.Converter.class)
    private Gender gender;
    private boolean smoke;

    @Enumerated(value = EnumType.STRING)
    private MBTI mbti;

    private boolean drinking;

    @Convert(converter = SleepPattern.Converter.class)
    private SleepPattern sleepPattern;
}
