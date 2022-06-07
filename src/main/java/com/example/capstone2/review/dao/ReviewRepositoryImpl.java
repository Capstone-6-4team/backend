package com.example.capstone2.review.dao;

import com.example.capstone2.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.capstone2.review.entity.QUserReview.userReview;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Double> getReviewAvg(User usr){
        return queryFactory.select(userReview.mannerScore.avg())
                .from(userReview).where(userReview.targetUser.eq(usr))
                .fetch();
    }
}
