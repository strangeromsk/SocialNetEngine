package ru.skillbox.socialnetworkimpl.sn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "city")
    private String title;
    @Column(name = "country_id")
    @JsonIgnore
    private int countryId;
    @ToString.Exclude
    @OneToMany(mappedBy = "town", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Person> persons;
}
