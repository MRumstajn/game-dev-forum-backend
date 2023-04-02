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
    private Long id;

    private Long userId;

    private Long postId;

    @Enumerated(EnumType.STRING)
    private PostReactionType postReactionType;
}
