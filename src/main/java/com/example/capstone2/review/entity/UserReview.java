package com.example.capstone2.review.entity;

import com.example.capstone2.common.entity.BaseEntity;
import com.example.capstone2.reservation.entitiy.Reservation;
import com.example.capstone2.user.entity.User;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
public class UserReview extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @NotNull
    @Range(min = 0, max = 5)
    private double mannerScore;

    private String comment;

    public static UserReview of(User writer, User targetUser, String comment, double score){
        UserReview userReview = new UserReview();
        userReview.writer=writer;
        userReview.targetUser=targetUser;
        userReview.comment=comment;
        userReview.mannerScore=score;

        return userReview;
    }
}
