package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.entity.User;

import javax.persistence.*;

@Entity
public class AvailableLanguage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
