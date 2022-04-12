package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.entity.Gender;
import com.example.capstone2.user.entity.User;

import javax.persistence.*;

@Embeddable
public class UserCharacteristic {

    @Convert(converter = Nationality.Converter.class)
    private Nationality nationality;

    @Convert(converter = Gender.Converter.class)
    private Gender gender;
    private boolean smoke;

    @Enumerated(value = EnumType.STRING)
    private String MBTI;

    private boolean drinking;

    private int bedTime;
    private int wakeUpTime;
}
