package com.mrumstajn.gamedevforum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "thread")
@Getter
@Setter
public class ForumThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;

    private String title;

    @ManyToOne
    private ForumUser author;

    private LocalDate creationDate;

    @OneToMany(mappedBy = "threadId")
    private List<Post> posts = new ArrayList<>();
}
