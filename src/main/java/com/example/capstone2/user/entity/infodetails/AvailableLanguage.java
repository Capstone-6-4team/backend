package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.entity.User;

import javax.persistence.*;

@Entity
public class AvailableLanguage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Convert(converter = Language.Converter.class)
    private Language language;

    public static AvailableLanguage of(Language lang, User user) {
        AvailableLanguage availableLanguage = new AvailableLanguage();
        availableLanguage.language = lang;
        availableLanguage.user = user;
        return availableLanguage;
    }
}
