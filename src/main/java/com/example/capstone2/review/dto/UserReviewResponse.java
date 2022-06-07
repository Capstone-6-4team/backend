package com.example.capstone2.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserReviewResponse {
    private String userName;
    private List<UserReviewDto> reviews;
    private Double average;

    public UserReviewResponse(List<UserReviewDto> userReviewDtos, Double avg, String name){
        this.userName=name;
        this.reviews=userReviewDtos;
        this.average=avg;
    }
}
