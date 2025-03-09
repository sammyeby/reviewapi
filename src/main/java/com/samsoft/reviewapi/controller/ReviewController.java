package com.samsoft.reviewapi.controller;

import com.samsoft.reviewapi.entities.Review;
import com.samsoft.reviewapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PutMapping("/{reviewId}/approve")
    public Review approveReview(@PathVariable Long reviewId) {
        return reviewService.approveReview(reviewId);
    }

    @PutMapping("/{reviewId}/reject")
    public Review rejectReview(@PathVariable Long reviewId) {
        return reviewService.rejectReview(reviewId);
    }
}