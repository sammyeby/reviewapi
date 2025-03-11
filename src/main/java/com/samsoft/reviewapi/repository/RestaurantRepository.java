package com.samsoft.reviewapi.repository;

import com.samsoft.reviewapi.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    Optional<Restaurant> findRestaurantsByNameAndZipcode(String name, String zipcode);
    List<Restaurant> findRestaurantsByZipcodeAndDairyScoreNotNullOrderByDairyScore(String zipcode);
    List<Restaurant> findRestaurantsByZipcodeAndEggScoreNotNullOrderByEggScore(String zipcode);
    List<Restaurant> findRestaurantsByZipcodeAndPeanutScoreNotNullOrderByPeanutScore(String zipcode);
}
