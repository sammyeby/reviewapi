package com.samsoft.reviewapi.controller;

import com.samsoft.reviewapi.entities.Restaurant;
import com.samsoft.reviewapi.service.RestaurantService;
import com.samsoft.reviewapi.util.ErrorResponseUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/restaurants")
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurants() {
        try {
            return ResponseEntity.ok(restaurantService.getAllRestaurants());
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error("Error getting all restaurants", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurant));
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error("Error creating restaurant", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restaurantService.getRestaurantById(id));
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error("Error getting restaurant by id", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchRestaurants(@RequestParam String zipcode, @RequestParam String allergy) {
        try {
            return ResponseEntity.ok(restaurantService.searchRestaurants(zipcode, allergy));
        } catch (ResponseStatusException e) {
            return ErrorResponseUtil.createErrorResponse(e.getReason(), e.getStatusCode());
        } catch (Exception e) {
            log.error("Error searching restaurants by zipcode and allergy", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
