package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.AccountEditEmailBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.AccountEditNotificationsBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.AccountEditPasswordBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponseApi;

import javax.servlet.http.HttpSession;

public interface AccountService {

    ResponseEntity<ResponseApi> signUpAccount(HttpSession session, String email, String password1,
                                              String password2, String firstName,
                                             String lastName, int code);

    ResponseEntity<ResponseApi> recoverPassword(HttpSession session, AccountEditPasswordBody accountEditPasswordBody);

    ResponseEntity<ResponseApi> setPassword(HttpSession session, AccountEditPasswordBody accountEditPasswordBody);

    ResponseEntity<ResponseApi> changeEmail(HttpSession session, AccountEditEmailBody accountEditEmailBody);

    ResponseEntity<ResponseApi> editNotifications(HttpSession session, AccountEditNotificationsBody accountEditNotificationsBody);
}
