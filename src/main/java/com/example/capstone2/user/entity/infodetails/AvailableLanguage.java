package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class AvailableLanguage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_characteristic_id")
    private UserCharacteristic userCharacteristic;

    @Enumerated(value = EnumType.STRING)
    private Language language;
}
