package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessageStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime time;

    @Column(name = "author_id")
    private int authorId;

    @Column(name = "recipient_id")
    private int recipientId;

    @Column(name = "message_text")
    private String messageText;

    @Enumerated(EnumType.STRING)
    @Column(name = "read_status")
    private MessageStatus readStatus;

    @Column(name = "dialog_id")
    private int dialogId;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}