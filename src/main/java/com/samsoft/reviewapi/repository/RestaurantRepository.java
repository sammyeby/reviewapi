package com.samsoft.reviewapi.repository;

import com.samsoft.reviewapi.entities.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    Optional<Restaurant> findByNameAndZipcode(String name, String zipcode);
//    List<Restaurant> findByZipcodeAndAverageEggScoreGreaterThanOrAverageDairyScoreGreaterThanOrAveragePeanutScoreGreaterThan(String zipcode, int averageEggScore, int averageDairyScore, int averagePeanutScore, Sort sort);
}
