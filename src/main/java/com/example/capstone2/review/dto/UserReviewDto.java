package com.example.capstone2.review.dto;

import com.example.capstone2.review.entity.UserReview;
import lombok.Getter;

@Getter
public class UserReviewDto {
    private String writerName;
    private String comment;
    private double score;

    public static UserReviewDto from(UserReview userReview){
        UserReviewDto userReviewDto =new UserReviewDto();
        userReviewDto.writerName = userReview.getWriter().getName();
        userReviewDto.comment = userReview.getComment();
        userReviewDto.score = userReview.getMannerScore();

        return userReviewDto;
    }
}
