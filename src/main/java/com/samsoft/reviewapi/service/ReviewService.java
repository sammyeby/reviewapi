package com.samsoft.reviewapi.service;

import com.samsoft.reviewapi.entities.Review;
import com.samsoft.reviewapi.entities.ReviewAction;
import com.samsoft.reviewapi.entities.User;
import com.samsoft.reviewapi.model.ReviewStatus;
import com.samsoft.reviewapi.entities.Restaurant;
import com.samsoft.reviewapi.repository.ReviewRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public ReviewService(ReviewRepository reviewRepository, UserService userService, RestaurantService restaurantService) {
        this.reviewRepository = reviewRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    public Iterable<Review> getAllReviews() {
        return reviewRepository.findAll();
    }


    public void addUserReview(Review review) {
        validateUserReview(review);

        Restaurant optionalRestaurant = restaurantService.getRestaurantById(review.getRestaurantId());
        if (ObjectUtils.isEmpty(optionalRestaurant)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Restaurant not found");
        }

        review.setStatus(ReviewStatus.PENDING);
        reviewRepository.save(review);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
    }


    public List<Review> getReviewsByStatus(String status) {
        ReviewStatus reviewStatus = ReviewStatus.PENDING;
        try {
            reviewStatus = ReviewStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid review status");
        }

        return reviewRepository.findReviewsByStatus(reviewStatus);
    }

    public void performReviewAction(Long reviewId, ReviewAction reviewAction) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

        Restaurant restaurant = restaurantService.getRestaurantById(review.getRestaurantId());

        if (reviewAction.getAccepted()) {
            review.setStatus(ReviewStatus.ACCEPTED);
        } else {
            review.setStatus(ReviewStatus.REJECTED);
        }
        reviewRepository.save(review);
        updateRestaurantReviewScores(restaurant);
    }


    public void validateUserReview(Review review) {
        if (ObjectUtils.isEmpty(review.getRestaurantId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant ID is required");
        }

        if (ObjectUtils.isEmpty(review.getSubmittedBy())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Submitted by is required");
        }

        User optionalUser = userService.getUserByDisplayName(review.getSubmittedBy());
        if (ObjectUtils.isEmpty(optionalUser)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "User not found");
        }


        if (ObjectUtils.isEmpty(review.getPeanutScore()) &&
                ObjectUtils.isEmpty(review.getDairyScore()) &&
                ObjectUtils.isEmpty(review.getEggScore())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one score is required");
        }

    }


    private void updateRestaurantReviewScores(Restaurant restaurant) {
        List<Review> reviews = reviewRepository.findReviewsByRestaurantIdAndStatus(restaurant.getId(), ReviewStatus.ACCEPTED);
        if (reviews.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No accepted reviews found for the restaurant");
        }

        // Calculate sums and counts for each score type
        int peanutSum = calculateSum(reviews, Review::getPeanutScore);
        int peanutCount = calculateCount(reviews, Review::getPeanutScore);
        int dairySum = calculateSum(reviews, Review::getDairyScore);
        int dairyCount = calculateCount(reviews, Review::getDairyScore);
        int eggSum = calculateSum(reviews, Review::getEggScore);
        int eggCount = calculateCount(reviews, Review::getEggScore);

        // Calculate overall score
        int totalCount = peanutCount + dairyCount + eggCount;
        int totalSum = peanutSum + dairySum + eggSum;
        float overallScore = (float) totalSum / totalCount;
        restaurant.setOverallScore(decimalFormat.format(overallScore));

        // Set individual scores if there are any reviews for that score type
        if (peanutCount > 0) {
            float peanutScore = (float) peanutSum / peanutCount;
            restaurant.setPeanutScore(decimalFormat.format(peanutScore));
        }

        if (dairyCount > 0) {
            float dairyScore = (float) dairySum / dairyCount;
            restaurant.setDairyScore(decimalFormat.format(dairyScore));
        }

        if (eggCount > 0) {
            float eggScore = (float) eggSum / eggCount;
            restaurant.setEggScore(decimalFormat.format(eggScore));
        }

        // Save the updated restaurant entity
        restaurantService.updateRestaurant(restaurant);
    }

    private int calculateSum(List<Review> reviews, Function<Review, Integer> scoreGetter) {
        return reviews.stream()
                .map(scoreGetter)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int calculateCount(List<Review> reviews, Function<Review, Integer> scoreGetter) {
        return (int) reviews.stream()
                .map(scoreGetter)
                .filter(Objects::nonNull)
                .count();
    }
}
