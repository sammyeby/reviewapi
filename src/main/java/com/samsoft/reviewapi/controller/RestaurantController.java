package com.samsoft.reviewapi.controller;

import com.samsoft.reviewapi.entities.Restaurant;
import com.samsoft.reviewapi.service.RestaurantService;
import com.samsoft.reviewapi.util.ErrorResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<Iterable<Restaurant>> getAllRestaurants() {
        try {
            return ResponseEntity.ok(restaurantService.getAllRestaurants());
        } catch (Exception e) {
            log.error("Error getting all restaurants", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurant));
        } catch (Exception e) {
            log.error("Error creating restaurant", e);
            return ErrorResponseUtil.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restaurantService.getRestaurantById(id));
        } catch (Exception e) {
            log.error("Error getting restaurant by id", e);
            return ErrorResponseUtil.createErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getRestaurantByNameAndZipcode(@RequestParam String name, @RequestParam String zipcode) {
        try {
            return ResponseEntity.ok(restaurantService.getRestaurantByNameAndZipcode(name, zipcode));
        } catch (Exception e) {
            log.error("Error getting restaurant by name and zipcode", e);
            return ErrorResponseUtil.createErrorResponse(e.getMessage());
        }
    }
}
