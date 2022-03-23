package com.example.capstone2.review.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.reservation.entitiy.Reservation;
import com.example.capstone2.user.entity.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class UserReview extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User targetUser;

    private int mannerScore;
    private String comment;
}
