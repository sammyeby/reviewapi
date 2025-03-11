package com.samsoft.reviewapi.entities;

import com.samsoft.reviewapi.model.ReviewStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    private Integer peanutScore;

    private Integer eggScore;

    private Integer dairyScore;

    private String commentary;

    private String submittedBy;

    private ReviewStatus status;

}
