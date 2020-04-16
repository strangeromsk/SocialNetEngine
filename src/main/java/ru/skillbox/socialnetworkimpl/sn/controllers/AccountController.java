package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.AccountEditEmailBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.AccountEditNotificationsBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.AccountEditPasswordBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponseApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.AccountService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/account/")
@ComponentScan("services")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping(value = "register", params = {"email", "passwd1", "passwd2", "first_name", "last_name", "code"})
    public ResponseEntity<ResponseApi> signUpAccount(HttpServletRequest request,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("passwd1") String password1,
                                                     @RequestParam("passwd2") String password2,
                                                     @RequestParam("first_name") String firstName,
                                                     @RequestParam("last_name") String lastName,
                                                     @RequestParam("code") int code) {
        return accountService.signUpAccount(request.getSession(), email, password1, password2, firstName, lastName, code);
    }

    @PutMapping("password/recovery")
    public ResponseEntity<ResponseApi> recoverPassword(HttpServletRequest request,
                                                       @RequestBody AccountEditPasswordBody accountEditPasswordBody) {
        return accountService.recoverPassword(request.getSession(), accountEditPasswordBody);
    }

    @PutMapping("password/set")
    public ResponseEntity<ResponseApi> setPassword(HttpServletRequest request,
                                                       @RequestBody AccountEditPasswordBody accountEditPasswordBody) {
        return accountService.setPassword(request.getSession(), accountEditPasswordBody);
    }

    @PutMapping("email")
    public ResponseEntity<ResponseApi> changeEmail(HttpServletRequest request,
                                                   @RequestBody AccountEditEmailBody accountEditEmailBody) {
        return accountService.changeEmail(request.getSession(), accountEditEmailBody);
    }

    @PutMapping("notifications")
    public ResponseEntity<ResponseApi> editNotifications(HttpServletRequest request,
                                                         @RequestBody AccountEditNotificationsBody accountEditNotificationsBody) {
        return accountService.editNotifications(request.getSession(), accountEditNotificationsBody);
    }
}
