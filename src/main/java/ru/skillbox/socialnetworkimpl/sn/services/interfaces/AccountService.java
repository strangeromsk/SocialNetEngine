package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;

import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;

public interface AccountService {

    ResponseEntity<ResponsePlatformApi> signUpAccount(String email, String passwd1,
                                  String passwd2, String firstName,
                                  String lastName, String code);

    ResponseEntity<ResponsePlatformApi> recoverPassword(String email);

    ResponseEntity<ResponsePlatformApi> setPassword(String token, String password);

    ResponseEntity<ResponsePlatformApi> changeEmail(String email);

    ResponseEntity<ResponsePlatformApi> editNotifications(String notification_type, boolean enable);

    default ResponsePlatformApi getOkResponse() {
        return ResponsePlatformApi.builder().error("string")
                .timestamp(new Date().getTime()).data(getOkMessage()).build();
    }

    default HashMap<String, String> getOkMessage() {
        HashMap<String,String> data = new HashMap<>();
        data.put("message","ok");
        return data;
    }

    default ResponsePlatformApi getErrorResponse(String description) {
         return ResponsePlatformApi.builder().error("invalid_request").error_description(description).build();
    }
}
