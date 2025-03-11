package com.samsoft.reviewapi.entities;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Restaurant {
    // An ID to uniquely identify the person we are storing in our db
    @Id
    @GeneratedValue
    private Long id;

    // The name of the restaurant
    private String name;

    // The address of the restaurant
    private String address;

    // The zipcode of the restaurant
    private String zipcode;

    // The phone number of the restaurant
    private String phoneNumber;

    // The website of the restaurant
    private String website;

    // Allergies scores for the restaurant

    private String overallScore;

    private String peanutScore;

    private String eggScore;

    private String dairyScore;
}
