package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.account.AccEmailBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.account.NotificationBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.account.RegisterBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.AccountServiceImpl;

@RestController
@RequestMapping("/api/v1/account/")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping("register")
    public ResponseEntity<ResponsePlatformApi> signUpAccount (@RequestBody RegisterBody newPerson) {
       return accountService.signUpAccount(newPerson.getEmail(), newPerson.getPasswd1(),
                newPerson.getPasswd2(), newPerson.getFirstName(), newPerson.getLastName(), newPerson.getCode());
    }

    @PutMapping("password/recovery")
    public ResponseEntity<ResponsePlatformApi> recovePassword (@RequestBody AccEmailBody accEmail) {
        return accountService.recoverPassword(accEmail.getEmail());
    }


    //TODO На вход приходит токен и пароль. Где брать токен. Его нигде нет у пользователя???
    @PutMapping("password/set")
    public ResponseEntity<ResponsePlatformApi> sePassword(@RequestBody AccEmailBody accEmail) {
        return null;
    }

    //TODO тот же вопрос идентификации
    @PutMapping("email")
    public ResponseEntity<ResponsePlatformApi> changeEmail(@RequestBody AccEmailBody accEmail) {
        return null;
    }

    //TODO Непонятно у какого пользователя менять.
    @PutMapping("notifications")
    public ResponseEntity<ResponsePlatformApi> changeNotifications(NotificationBody notificationBody) {
        return null;
    }

}
