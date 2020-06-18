package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.Data;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.NotificationTypeCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification_type")
public class NotificationType {
    @Id
    private int id;

    @Column(name = "code", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationTypeCode code;

    @Column(name = "name", nullable = false)
    private String name;
}