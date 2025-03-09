package com.samsoft.reviewapi.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Data
@Table(name = "restaurants")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restaurant {
    // An ID to uniquely identify the person we are storing in our db
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The name of the restaurant
    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    // The address of the restaurant
    @NotBlank(message = "Address is mandatory")
    @Column(nullable = false)
    private String address;

    // The zipcode of the restaurant
    @NotBlank(message = "Zipcode is mandatory")
    @Column(nullable = false)
    private String zipcode;

    // The phone number of the restaurant
    @NotBlank(message = "Phone is mandatory")
    @Column(nullable = false)
    private String phone;

    // Allergies scores for the restaurant
    @Max(5)
    @Column
    private Integer averagePeanutScore;

    @Max(5)
    @Column
    private Integer averageEggScore;

    @Max(5)
    @Column
    private Integer averageDairyScore;
}
