package com.samsoft.reviewapi;

import com.samsoft.reviewapi.entities.Restaurant;
import com.samsoft.reviewapi.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;

    public DataLoader(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create 10 dummy restaurants
        for (int i = 1; i <= 10; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Restaurant " + i);
            restaurant.setAddress("Address " + i);
            restaurant.setPhone("123456789" + (i - 1));
            restaurant.setZipcode("1234" + (i - 1));
            restaurant.setAverageEggScore(Math.min((int) (Math.random() * 6), 5));
            restaurant.setAverageDairyScore(Math.min((int) (Math.random() * 6), 5));
            restaurant.setAveragePeanutScore(Math.min((int) (Math.random() * 6), 5));
            restaurantRepository.save(restaurant);
        }
    }
}