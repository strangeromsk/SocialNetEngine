package ru.skillbox.socialnetworkimpl.sn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessagesPermission;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String fistName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "reg_date", nullable = false, columnDefinition = "DATE")
    private LocalDate regDate;

    @Column(name = "birth_date", nullable = false, columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "e_mail", nullable = false)
    private String email;

    @Column(length = 11)
    private int phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 2048)
    private String photo;

    @Column(nullable = false, length = 2048)
    private String about;

    @Column(nullable = false, length = 45)
    private String town;

    @Column(name = "confirmation_code", nullable = false, length = 45)
    private String confirmationCode;

    @Column(name = "is_approved", nullable = false, columnDefinition = "BIT")
    private byte isApproved;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "messages_permission")
    private MessagesPermission messagesPermission;

    @Column(name = "last_online_time", nullable = false)
    private LocalDateTime lastOnlineTime;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "BIT")
    private byte isBlocked;

    @JsonIgnoreProperties
    private String token;
}
