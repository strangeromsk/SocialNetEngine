package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.account.RegisterRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.AccountService;

@RestController
@RequestMapping("account/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("register")
    public ResponseEntity<ResponsePlatformApi> signUpAccount(@RequestBody RegisterRequestBody newPerson) {
        return accountService.signUpAccount(newPerson.getEmail(), newPerson.getPasswd1(),
                newPerson.getPasswd2(), newPerson.getFirstName(), newPerson.getLastName(), newPerson.getCode());
    }

    @PutMapping("password/recovery")

    public ResponseEntity<ResponsePlatformApi> recoverPassword(@RequestBody RegisterRequestBody accEmail) {
        return accountService.recoverPassword(accEmail.getEmail());
    }

    //TODO Тут и далее все манипуляции производятся над пользователем №1 до реализации Security
    @PutMapping("password/set")
    public ResponseEntity<ResponsePlatformApi> sePassword(@RequestBody RegisterRequestBody tokenAndPassword) {
        return accountService.setPassword(tokenAndPassword.getToken(), tokenAndPassword.getPassword());
    }


    @PutMapping("email")
    public ResponseEntity<ResponsePlatformApi> changeEmail(@RequestBody RegisterRequestBody accEmail) {
        return accountService.changeEmail(accEmail.getEmail());
    }

    @PutMapping("notifications")
    public ResponseEntity<ResponsePlatformApi> changeNotifications(@RequestBody RegisterRequestBody notificationsBody) {
        return accountService.editNotifications(notificationsBody.getNotificationType(),notificationsBody.isEnable());
    }

}
