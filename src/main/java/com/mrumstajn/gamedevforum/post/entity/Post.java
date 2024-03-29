package com.mrumstajn.gamedevforum.post.entity;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.thread.entity.ForumThread;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE post SET deleted = true WHERE id=?")
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

    @ManyToMany(mappedBy = "postId")
    private List<UserPostReaction> userPostReactions;
}
