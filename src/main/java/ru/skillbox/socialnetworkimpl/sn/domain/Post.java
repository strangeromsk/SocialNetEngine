package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "author_id", nullable = false, length = 11)
    private int authorId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "post_text", nullable = false)
    private String postText;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "BIT")
    private byte isBlocked;
}
