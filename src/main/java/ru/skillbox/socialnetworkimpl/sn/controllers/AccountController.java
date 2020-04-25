package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.RecoverPasswordBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.RegisterBody;
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
    public ResponseEntity<ResponsePlatformApi> recovePassword (@RequestBody RecoverPasswordBody recoverEmail) {
        return accountService.recoverPassword(recoverEmail.getEmail());
       // return null;
    }
}
