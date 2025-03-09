package com.samsoft.reviewapi.service;

import com.samsoft.reviewapi.entities.Restaurant;
import com.samsoft.reviewapi.repository.RestaurantRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Iterable<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findByNameAndZipcode(restaurant.getName(), restaurant.getZipcode());
        if (existingRestaurant.isPresent()) {
            throw new RuntimeException("Restaurant with the same name and zipcode already exists");
        }
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return Optional.ofNullable(restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found")));
    }

    public Optional<Restaurant> getRestaurantByNameAndZipcode(String name, String zipcode) {
        return restaurantRepository.findByNameAndZipcode(name, zipcode);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurant.getId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setName(existingRestaurant.getName());
        restaurant.setZipcode(existingRestaurant.getZipcode());
        return restaurantRepository.save(restaurant);
    }

//    public List<Restaurant> getRestaurantsByAllergiesScores(String zipcode, int eggScore, int dairyScore, int peanutScore, Sort sort) {
//
//        return restaurantRepository.findByZipcodeAndAverageEggScoreGreaterThanOrAverageDairyScoreGreaterThanOrAveragePeanutScoreGreaterThan(zipcode, eggScore, dairyScore, peanutScore, sort);
//    }

}
