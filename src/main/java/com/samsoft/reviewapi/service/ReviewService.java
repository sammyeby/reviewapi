package com.samsoft.reviewapi.service;

import com.samsoft.reviewapi.entities.Review;
import com.samsoft.reviewapi.model.ReviewStatus;
import com.samsoft.reviewapi.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
//        System.out.println("Restaurant Name: " + review.getName());

        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review approveReview(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setReviewStatus(ReviewStatus.ACCEPTED);
            return reviewRepository.save(review);
        } else {
            throw new RuntimeException("Review not found");
        }
    }

    public Review rejectReview(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            review.setReviewStatus(ReviewStatus.REJECTED);
            return reviewRepository.save(review);
        } else {
            throw new RuntimeException("Review not found");
        }
    }
}
