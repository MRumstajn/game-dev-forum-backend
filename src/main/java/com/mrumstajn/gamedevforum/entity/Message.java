package com.mrumstajn.gamedevforum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime creationDateTime;

    @ManyToOne
    private ForumUser author;

    @ManyToOne
    private Conversation conversation;

    @ManyToMany
    @JoinTable(
            name = "message_reader",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "reader_id"))
    private List<ForumUser> readers;
}
