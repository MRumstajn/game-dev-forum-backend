package com.mrumstajn.gamedevforum.notification.entity;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ForumUser recipient;

    private String title;

    private String content;

    private LocalDate creationDate;

    private Boolean isRead = false;
}
