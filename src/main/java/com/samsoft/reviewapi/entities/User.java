package com.samsoft.reviewapi.entities;

import jakarta.persistence.*;
import lombok.Data;
//import lombok.EqualsAndHashCode;

//@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

//    A user consists of the following info:
//
//    their display name, one that’s unique to only that user
//    city
//            state
//    zipcode
//    whether they’re interested in peanut allergies
//    whether they’re interested in egg allergies
//    whether they’re interested in dairy allergies

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipcode;

    @Column
    private boolean peanutAllergy;

    @Column
    private boolean eggAllergy;

    @Column
    private boolean dairyAllergy;

}
