package ru.skillbox.socialnetworkimpl.sn.api.requests.account;

import lombok.Data;

@Data
public class RegisterBody {
    private String email;
    private String passwd1;
    private String passwd2;
    private String firstName;
    private String lastName;
    private String code;


}
