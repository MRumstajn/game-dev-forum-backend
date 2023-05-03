package com.mrumstajn.gamedevforum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_a_id")
    private ForumUser participantA;

    @ManyToOne
    @JoinColumn(name = "participant_b_id")
    private ForumUser participantB;

}
