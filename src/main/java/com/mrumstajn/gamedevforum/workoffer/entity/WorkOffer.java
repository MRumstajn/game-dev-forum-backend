package com.mrumstajn.gamedevforum.workoffer.entity;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class WorkOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private ForumUser author;

    private BigDecimal pricePerHour;

    private Character currencySymbol;

    @ManyToOne
    private WorkOfferCategory workOfferCategory;
}
