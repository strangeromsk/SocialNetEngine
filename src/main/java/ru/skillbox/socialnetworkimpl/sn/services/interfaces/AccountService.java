package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

import javax.servlet.http.HttpSession;

public interface AccountService {

    ResponseEntity<ResponsePlatformApi> signUpAccount(HttpSession session, String email, String passwd1,
                                                      String passwd2, String firstName,
                                                      String lastName, int code);

    ResponseEntity<ResponsePlatformApi> recoverPassword(HttpSession session, String email);

    ResponseEntity<ResponsePlatformApi> setPassword(HttpSession session, String token, String password);

    ResponseEntity<ResponsePlatformApi> changeEmail(HttpSession session, String email);

    ResponseEntity<ResponsePlatformApi> editNotifications(HttpSession session, String notification_type, boolean enable);
}
