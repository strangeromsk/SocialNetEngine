package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;

    private String title;

    @Column(length = 4096)
    private String postText;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PostComment> comments;
}

