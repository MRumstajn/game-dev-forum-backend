package com.mrumstajn.gamedevforum.thread.entity;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ThreadSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ForumThread thread;

    @ManyToOne
    private ForumUser user;

}
