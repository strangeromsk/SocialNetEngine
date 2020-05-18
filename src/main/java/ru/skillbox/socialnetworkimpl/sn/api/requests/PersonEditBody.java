package ru.skillbox.socialnetworkimpl.sn.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessagesPermission;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@Builder
public class PersonEditBody {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("birth_date")
    private long birthDate;
    private String phone;
    @JsonProperty("photo_id")
    private String photo;
    private String about;
    @JsonProperty("town_id")
    private int city;
    private City town;
    @JsonProperty("country_id")
    private int countryId;
//    private Country country;
    @JsonProperty("messages_permission")
    @Enumerated(EnumType.STRING)
    private MessagesPermission messagesPermission;
}
