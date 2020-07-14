package ru.skillbox.socialnetworkimpl.sn.api.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessagesPermission;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonRequest {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("birth_date")
    private Date birthDate;
    private String phone;
    @JsonProperty("photo_id")
    private String photo;
    private String about;
    @JsonProperty("town_id")
    private int townId;

    private String city;
    private City town;
    private String country;

    @JsonProperty("country_id")
    private int countryId;
    @JsonProperty("messages_permission")
    @Enumerated(EnumType.STRING)
    private MessagesPermission messagesPermission;
}
