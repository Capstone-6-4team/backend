package com.example.capstone2.user.entity.infodetails;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.user.entity.Gender;
import com.example.capstone2.user.entity.User;

import javax.persistence.*;

@Entity
public class UserCharacteristic extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private Nationality nationality;



    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private boolean smoke;

    @Column(length = 4)
    private String MBTI;

    private boolean drinking;

    private int bedTime;
    private int wakeUpTime;

}
