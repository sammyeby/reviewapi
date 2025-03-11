package com.samsoft.reviewapi.repository;

import com.samsoft.reviewapi.entities.Review;
import com.samsoft.reviewapi.model.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findReviewsByStatus(ReviewStatus status);
    List<Review> findReviewsByRestaurantIdAndStatus(Long restaurantId, ReviewStatus status);
}
