package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.account.AccountRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.AccountServiceImpl;

@RestController
@RequestMapping("/api/v1/account/")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping("register")
    public ResponseEntity<ResponsePlatformApi> signUpAccount (@RequestBody AccountRequestBody newPerson) {
       return accountService.signUpAccount(newPerson.getEmail(), newPerson.getPasswd1(),
                newPerson.getPasswd2(), newPerson.getFirstName(), newPerson.getLastName(), newPerson.getCode());
    }

    @PutMapping("password/recovery")
    public ResponseEntity<ResponsePlatformApi> recovePassword (@RequestBody AccountRequestBody accEmail) {
        return accountService.recoverPassword(accEmail.getEmail());
    }


    //TODO Тут и далее все манипуляции производятся над пользователем №1 до реализации Security
    @PutMapping("password/set")
    public ResponseEntity<ResponsePlatformApi> sePassword(@RequestBody AccountRequestBody tokenAndPassword) {
        return accountService.setPassword(tokenAndPassword.getToken(), tokenAndPassword.getPassword());
    }


    @PutMapping("email")
    public ResponseEntity<ResponsePlatformApi> changeEmail(@RequestBody AccountRequestBody accEmail) {
        return accountService.changeEmail(accEmail.getEmail());
    }

    //TODO Непонятно у какого пользователя менять.
    @PutMapping("notifications")
    public ResponseEntity<ResponsePlatformApi> changeNotifications(AccountRequestBody notificationsBody) {
        return null;
    }

}
