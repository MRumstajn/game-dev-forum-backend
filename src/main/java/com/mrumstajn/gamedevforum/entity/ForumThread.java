package com.mrumstajn.gamedevforum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "thread")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE thread SET deleted = true WHERE id=?")
public class ForumThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Category category;

    private String title;

    @ManyToOne
    private ForumUser author;

    private LocalDateTime creationDateTime;

    private Boolean deleted = false;
}
