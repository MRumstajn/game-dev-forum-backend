package com.mrumstajn.gamedevforum.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"content"}, allowGetters = true)
@SQLDelete(sql = "UPDATE message SET deleted = true WHERE id = ?")
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

    private Boolean deleted;

    @JsonProperty("content")
    public String getContent() {
        if (deleted == null){
            return content;
        }

        return !deleted ? content : null;
    }
}
