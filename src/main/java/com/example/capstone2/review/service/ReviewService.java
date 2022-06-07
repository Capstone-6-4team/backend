package com.example.capstone2.review.service;

import com.example.capstone2.review.dao.ReviewRepository;
import com.example.capstone2.review.dao.ReviewRepositoryCustom;
import com.example.capstone2.review.dto.UserReviewDto;
import com.example.capstone2.review.dto.UserReviewResponse;
import com.example.capstone2.review.entity.UserReview;
import com.example.capstone2.user.entity.User;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewRepositoryCustom reviewRepositoryCustom;

    public UserReviewResponse getReviewList(User user){
        List<UserReviewDto> userReviewDtoList = new ArrayList<>();

        List<UserReview> reviewList = reviewRepository.findByTargetUser(user);
        List<Double> reviewAvg = reviewRepositoryCustom.getReviewAvg(user);

        for(Double d : reviewAvg){
            System.out.println("d = " + d);
        }

        reviewList.forEach((review)->userReviewDtoList.add(UserReviewDto.from(review)));

        return new UserReviewResponse(userReviewDtoList, reviewAvg.get(0), user.getName());
    }

    public Long registerReview(User writer, User targetUser, String comment, double score){
        UserReview review = UserReview.of(writer, targetUser, comment, score);
        return reviewRepository.save(review).getId();
    }
}
