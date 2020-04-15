package ru.skillbox.socialnetworkimpl.sn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String city;
    @Column(name = "country_id")
    @JsonIgnore
    private int countryId;
}
