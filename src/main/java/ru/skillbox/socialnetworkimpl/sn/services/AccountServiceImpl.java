package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.ErrorMessages;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessagesPermission;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.AccountService;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class AccountServiceImpl implements AccountService {
    private final String SUBJECT = "This is you password recovery code";

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmailMessageService emailMessageService;

    @Override
    public ResponseEntity<ResponsePlatformApi> signUpAccount(String email, String passwd1, String passwd2, String firstName, String lastName, String code) {
        if (!passwd1.equals(passwd2) || !isEmailCorrect(email)) {
            return new ResponseEntity<>(getErrorResponse(ErrorMessages.PASS_EMAIL_INC.getTitle())
                    , HttpStatus.BAD_REQUEST);
        }
        if (getCurrentUser(email) != null)
            return new ResponseEntity<>(getErrorResponse(ErrorMessages.USER_EXISTS.getTitle()), HttpStatus.BAD_REQUEST);

        personRepository.save(Person.builder().email(email).firstName(firstName).lastName(lastName)
                .confirmationCode(code).password(passwd1).regDate(LocalDate.now())
                .messagesPermission(MessagesPermission.ALL).build());
        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> recoverPassword(String email) {
        if (!isEmailCorrect(email))
            return getIncorrectEmailResponse();

        Person currentPerson = getCurrentUser(email);
        if (currentPerson == null)
            return new ResponseEntity<>(getErrorResponse(ErrorMessages.USER_NOTEXIST.getTitle()), HttpStatus.BAD_REQUEST);

        emailMessageService.sendMessage(email, SUBJECT, currentPerson.getConfirmationCode());

        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    /**
     * Тут и далее просто проверяем что в токен что-то пришло, а все манипуляции выполняем над пользователем с
     * id = 1
     **/
    //TODO Тут и далее: переделать на получение пользователя в зависимости от токена после реализации Security
    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> setPassword(String token, String password) {
        if (token == null)
            return getUserInvalidResponse();

        Person currentUser = getCurrentUser("paul@mail.ru");
        currentUser.setPassword(password);
        personRepository.save(currentUser);

        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> changeEmail(String email) {
        // Заглушка на проверку пользователя
        boolean isAuthorized = true;
        if (!isAuthorized)
            return getUserInvalidResponse();

        if (!isEmailCorrect(email))
            return getIncorrectEmailResponse();

        if (personRepository.findByEmail(email) != null)
            return new ResponseEntity<>(getErrorResponse(ErrorMessages.USER_EXISTS.getTitle()), HttpStatus.BAD_REQUEST);
        //это ответ - юзер с таким e-mail уже существует. Можно сделать конкретно такой.

        Person currentUser = getCurrentUser("paul@mail.ru");
        currentUser.setEmail(email);
        personRepository.save(currentUser);

        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> editNotifications(String notification_type, boolean enable) {
        return null;
    }

    private boolean isEmailCorrect(String email) {
        return email.matches(".+@.+\\..{2,5}");
    }

    public Person getCurrentUser(String email) {
        return personRepository.findByEmail(email);
    }

    protected ResponseEntity getInternalErrorResponse() {
        return new ResponseEntity<>(getErrorResponse(ErrorMessages.INTERNAL.getTitle()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity getUserInvalidResponse() {
        return new ResponseEntity<>(getErrorResponse(ErrorMessages.USER_INVALID.getTitle()), HttpStatus.UNAUTHORIZED);
    }

    protected ResponseEntity getIncorrectEmailResponse() {
        return new ResponseEntity<>(getErrorResponse(ErrorMessages.INCORRECT_EMAIL.getTitle()), HttpStatus.BAD_REQUEST);
    }
}
