package com.samsoft.reviewapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "`user`")
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String displayName;

    private String city;
    private String state;
    private String zipcode;

    private Boolean eggCheck;
    private Boolean peanutCheck;
    private Boolean dairyCheck;
}
