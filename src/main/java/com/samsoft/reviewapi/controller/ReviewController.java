package com.samsoft.reviewapi.controller;

import com.samsoft.reviewapi.entities.Review;
import com.samsoft.reviewapi.service.ReviewService;
import com.samsoft.reviewapi.util.ErrorResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<?> addUserReview(@RequestBody Review review) {
        try {
            reviewService.addUserReview(review);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();

        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUserReviews() {
        try {
            return ResponseEntity.ok(reviewService.getAllReviews());
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserReviewById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reviewService.getReviewById(id));
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}