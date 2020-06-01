package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "notification_settings")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "person_id")
    private int personId;
    @Column(name = "notification_type_id")
    private int notificationTypeId;
    private boolean enable;
}
