package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "post")
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;

    @Column(name = "post_id", length = 11, nullable = false)
    private int postId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path", nullable = false)
    private String path;
}
