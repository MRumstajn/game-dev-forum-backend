package com.mrumstajn.gamedevforum.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class ForumUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String passwordHash;

    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    private ForumUserRole role;

    private String bio;
}
