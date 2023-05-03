package com.mrumstajn.gamedevforum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE thread SET deleted = true WHERE id=?")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ForumUser author;

    private String content;

    private LocalDateTime creationDateTime;

    @ManyToOne
    private ForumThread thread;

    private Boolean deleted = false;
}
