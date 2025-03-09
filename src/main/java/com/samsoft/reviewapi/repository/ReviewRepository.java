package com.samsoft.reviewapi.repository;

import com.samsoft.reviewapi.entities.Review;
import com.samsoft.reviewapi.model.ReviewStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findByRestaurantId(Long restaurantId);
    List<Review> findByReviewStatusEquals(ReviewStatus status);
    List<Review> findByRestaurantIdAndReviewStatusEquals(Long restaurantId, ReviewStatus status);
}
