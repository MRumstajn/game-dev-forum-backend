package com.mrumstajn.gamedevforum.workoffer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE work_offer_category SET deleted = true WHERE id=?")
@Getter
@Setter
public class WorkOfferCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;
}
