package com.mrumstajn.gamedevforum.workoffer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WorkOfferRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workOfferId;

    private Integer rating;

    private Long userId;
}
