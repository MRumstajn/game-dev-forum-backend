package com.mrumstajn.gamedevforum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_post_reaction")
@Getter
@Setter
public class UserPostReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long postId;

    @Column
    @Enumerated(EnumType.STRING)
    private PostReactionType postReactionType;
}
