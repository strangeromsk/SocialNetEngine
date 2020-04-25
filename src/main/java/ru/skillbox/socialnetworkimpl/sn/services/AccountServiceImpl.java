package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessagesPermission;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.AccountService;

import java.time.LocalDate;
import java.util.HashMap;

//TODO Запилить проверку e-mail регуляркой


@Component
public class AccountServiceImpl implements AccountService {
    private final String RECOVERY_MESSAGE = "This is a recovery message";
    private final String SUBJECT = "This is you password recovery link";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EmailMessageService emailMessageService;

    @Override
    public ResponseEntity<ResponsePlatformApi> signUpAccount(String email, String passwd1, String passwd2, String firstName, String lastName, String code) {
        if (passwd1.equals(passwd2) && email.contains("@")) {
            personRepository.save(Person.builder().email(email).fistName(firstName).lastName(lastName)
                            .confirmationCode(code).password(passwd1).regDate(LocalDate.now())
                            .messagesPermission(MessagesPermission.ALL).build());
            return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
        } else {
             return new ResponseEntity<>(getErrorResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> recoverPassword(String email) {
        if (email.contains("@")) {
            emailMessageService.sendMessage(email, SUBJECT, RECOVERY_MESSAGE);
            return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(getErrorResponse(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> setPassword(String token, String password) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> changeEmail(String email) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> editNotifications(String notification_type, boolean enable) {
        return null;
    }
}
