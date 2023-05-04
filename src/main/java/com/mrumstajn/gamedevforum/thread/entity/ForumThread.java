package com.mrumstajn.gamedevforum.thread.entity;

import com.mrumstajn.gamedevforum.category.entity.Category;
import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @ManyToOne
    private Category category;

    private String title;

    @ManyToOne
    private ForumUser author;

    private LocalDateTime creationDateTime;

    private Boolean deleted = false;
}
