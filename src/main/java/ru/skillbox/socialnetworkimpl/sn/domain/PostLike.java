package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_like")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @Column(name = "person_id", nullable = false, length = 11)
    private int personId;

    @Column(name = "post_id", nullable = false, length = 11)
    private int postId;
}
