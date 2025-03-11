package com.samsoft.reviewapi.controller;

import com.samsoft.reviewapi.entities.ReviewAction;
import com.samsoft.reviewapi.service.ReviewService;
import com.samsoft.reviewapi.util.ErrorResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RequestMapping("/admin")
@RestController
public class AdminController {
    private final ReviewService reviewService;

    public AdminController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getReviewsByStatus(@RequestParam String status) {
        try {
            return ResponseEntity.ok(reviewService.getReviewsByStatus(status));
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<?> performReviewAction(@PathVariable Long reviewId, @RequestBody ReviewAction reviewAction) {
        try {
            reviewService.performReviewAction(reviewId, reviewAction);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
