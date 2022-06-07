package com.example.capstone2.review.dao;


import com.example.capstone2.review.entity.UserReview;
import com.example.capstone2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<UserReview, Long> {
    List<UserReview> findByTargetUser(User user);
}
