package com.samsoft.reviewapi.service;

import com.samsoft.reviewapi.entities.Restaurant;
import com.samsoft.reviewapi.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class RestaurantService {
    private final Pattern zipcodePattern = Pattern.compile("\\d{5}");
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Iterable<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        validateRestaurant(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
    }


    public void updateRestaurant(Restaurant updatedRestaurant) {
        restaurantRepository.save(updatedRestaurant);
    }

    public Iterable<Restaurant> searchRestaurants(String zipcode, String allergy) {
        Iterable<Restaurant> restaurants = Collections.emptyList();
        if (allergy.equalsIgnoreCase("peanut")) {
            restaurants = restaurantRepository.findRestaurantsByZipcodeAndPeanutScoreNotNullOrderByPeanutScore(zipcode);
        } else if (allergy.equalsIgnoreCase("dairy")) {
            restaurants = restaurantRepository.findRestaurantsByZipcodeAndDairyScoreNotNullOrderByDairyScore(zipcode);
        } else if (allergy.equalsIgnoreCase("egg")) {
            restaurants = restaurantRepository.findRestaurantsByZipcodeAndEggScoreNotNullOrderByEggScore(zipcode);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid allergy");
        }
        return restaurants;
    }

    public void validateZipcode(String zipcode) {
        if (!zipcodePattern.matcher(zipcode).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid zipcode");
        }
    }

    public void validateRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant name is required");
        }
        if (restaurant.getZipcode() == null || restaurant.getZipcode().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant zipcode is required");
        }
        validateZipcode(restaurant.getZipcode());

        Optional<Restaurant> existingRestaurant = restaurantRepository.findRestaurantsByNameAndZipcode(restaurant.getName(), restaurant.getZipcode());
        if (existingRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Restaurant already exists");
        }
    }

}
