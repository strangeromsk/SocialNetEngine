package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.Data;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.FriendshipStatus;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "src_person_id", nullable = false)
    private int srcPersonId;

    @Column(name = "dst_person_id", nullable = false)
    private int dstPersonId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "code", nullable = false)
    private FriendshipStatus code;
}