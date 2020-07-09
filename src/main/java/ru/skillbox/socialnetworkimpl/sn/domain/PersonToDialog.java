package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(IdForPersonToDialog.class)
@Table(name = "person2dialog")
public class PersonToDialog {

    @Id
    @Column(name = "person_id")
    private int personId;

    @Id
    @Column(name = "dialog_id")
    private int dialogId;
}
