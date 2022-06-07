package com.example.capstone2.review.dao;

import com.example.capstone2.user.entity.User;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Double> getReviewAvg(User user);
}
