package com.mrumstajn.gamedevforum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ForumUser author;

    private String content;

    private LocalDateTime creationDateTime;

    private Long threadId;

    private Long likes = 0L;

    private Long dislikes = 0L;
}
