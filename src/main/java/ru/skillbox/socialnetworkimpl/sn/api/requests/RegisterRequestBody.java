package ru.skillbox.socialnetworkimpl.sn.api.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequestBody {
    private String email;
    private String passwd1;
    private String passwd2;
    private String firstName;
    private String lastName;
    private String code;
    private String password;
    private String token;
    @JsonProperty("notification_type")
    private String notificationType;
    private boolean enable;



}
