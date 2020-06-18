package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessagesPermission;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "reg_date", nullable = false, columnDefinition = "DATE")
    private LocalDate regDate;

    @Column(name = "birth_date",columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "e_mail", nullable = false)
    private String email;

    @Column(length = 11)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(length = 2048)
    private String photo;

    @Column(length = 2048)
    private String about;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "town")
    private City town;

    @Column(name = "confirmation_code", nullable = false, length = 45)
    private String confirmationCode;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "messages_permission")
    private MessagesPermission messagesPermission;

    @Column(name = "last_online_time")
    private LocalDateTime lastOnlineTime;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostComment> postComments;
}
