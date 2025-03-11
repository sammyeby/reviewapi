package com.samsoft.reviewapi;

import com.samsoft.reviewapi.entities.Restaurant;
import com.samsoft.reviewapi.entities.Review;
import com.samsoft.reviewapi.entities.User;
import com.samsoft.reviewapi.model.ReviewStatus;
import com.samsoft.reviewapi.repository.RestaurantRepository;
import com.samsoft.reviewapi.repository.ReviewRepository;
import com.samsoft.reviewapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class DataLoader implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public DataLoader(RestaurantRepository restaurantRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create 5 dummy restaurants
        for (int i = 1; i <= 5; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Restaurant " + i);
            restaurant.setAddress("Address " + i);
            restaurant.setZipcode("1234" + i);
            restaurant.setPhoneNumber("123456789" + i);
            restaurant.setWebsite("http://restaurant" + i + ".com");
            restaurant.setOverallScore("4." + i);
            restaurant.setPeanutScore("4." + i);
            restaurant.setEggScore("4." + i);
            restaurant.setDairyScore("4." + i);
            restaurantRepository.save(restaurant);
        }

        // Create 5 dummy users
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setDisplayName("User " + i);
            user.setCity("City " + i);
            user.setState("State " + i);
            user.setZipcode("5678" + i);
            user.setEggCheck(i % 2 == 0);
            user.setPeanutCheck(i % 2 != 0);
            user.setDairyCheck(i % 3 == 0);
            userRepository.save(user);
        }

        // Create 5 dummy reviews
        List<Restaurant> restaurants = StreamSupport.stream(restaurantRepository.findAll().spliterator(), false)
                .toList();
        for (int i = 1; i <= 5; i++) {
            Review review = new Review();
            review.setRestaurantId(restaurants.get(i - 1).getId());
            review.setPeanutScore(i);
            review.setEggScore(i + 1);
            review.setDairyScore(i + 2);
            review.setCommentary("Commentary " + i);
            review.setSubmittedBy("User " + i);
            review.setStatus(ReviewStatus.ACCEPTED);
            reviewRepository.save(review);
        }
    }
}