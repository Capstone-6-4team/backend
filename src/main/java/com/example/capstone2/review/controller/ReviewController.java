package com.example.capstone2.review.controller;

import com.example.capstone2.review.dto.UserReviewResponse;
import com.example.capstone2.review.service.ReviewService;
import com.example.capstone2.user.entity.User;
import com.example.capstone2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/{id}/info")
    public ResponseEntity<UserReviewResponse> getReviews(@PathVariable Long id){
        User user = userService.findById(id);
        UserReviewResponse reviewInfo = reviewService.getReviewList(user);

        return ResponseEntity.ok(reviewInfo);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerReview(Authentication authentication,
                                            @RequestParam Long targetUserId,
                                            @RequestParam String comment,
                                            @RequestParam double score){
        String email = authentication.getName();
        User writer = userService.findByEmail(email);
        User targetUser = userService.findById(targetUserId);

        Long id = reviewService.registerReview(writer, targetUser, comment, score);

        return ResponseEntity.ok(id);
    }
}
