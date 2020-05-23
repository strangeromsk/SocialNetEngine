package ru.skillbox.socialnetworkimpl.sn.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonEditBody {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private long birthDate;
    private String phone;

    @JsonProperty("photo_id")
    private String photoId;
    private String about;

    @JsonProperty("town_id")
    private int townId;

    @JsonProperty("country_id")
    private int countryId;
    private String messagePermission;
}
