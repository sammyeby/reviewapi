package com.samsoft.reviewapi.entities;

import com.samsoft.reviewapi.model.ReviewStatus;
import jakarta.persistence.*;
import lombok.Data;
//import lombok.EqualsAndHashCode;

//@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    //    A dining review consists of the following info:
//
//    who submitted, represented by their unique display name (String)
//    the restaurant, represented by its Id (Long)
//    an optional peanut score, on a scale of 1-5
//    an optional egg score, on a scale of 1-5
//    an optional dairy score, on a scale of 1-5
//    an optional commentary, represented by a String
    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private Long restaurantId;

    @Column
    private Integer peanutScore;

    @Column
    private Integer eggScore;

    @Column
    private Integer dairyScore;

    @Column
    private String commentary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus reviewStatus = ReviewStatus.PENDING;

}
