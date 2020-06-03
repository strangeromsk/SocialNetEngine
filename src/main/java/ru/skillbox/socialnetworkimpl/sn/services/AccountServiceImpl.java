package ru.skillbox.socialnetworkimpl.sn.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.domain.NotificationSettings;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.ErrorMessages;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessagesPermission;
import ru.skillbox.socialnetworkimpl.sn.repositories.NotificationSettingsRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.NotificationTypeRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.AccountService;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class AccountServiceImpl implements AccountService {
    private final String SUBJECT = "This is you password recovery code";

    private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmailMessageService emailMessageService;

    @Autowired
    private NotificationSettingsRepository notificationSettingsRepository;
    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Override
    public ResponseEntity<ResponsePlatformApi> signUpAccount(String email, String passwd1, String passwd2,
                                                             String firstName, String lastName, String code) {
        if (!passwd1.equals(passwd2) || !isEmailCorrect(email)) {
            return new ResponseEntity<>(getErrorResponse(ErrorMessages.PASS_EMAIL_INC.getTitle())
                    , HttpStatus.BAD_REQUEST);
        }
        if (personRepository.findByEmail(email) != null)
            return new ResponseEntity<>(getErrorResponse(ErrorMessages.USER_EXISTS.getTitle()), HttpStatus.BAD_REQUEST);

        personRepository.save(Person.builder().email(email).firstName(firstName).lastName(lastName)
                .confirmationCode(code).password(bcryptEncoder.encode(passwd1))
                .regDate(LocalDate.now())
                .isBlocked(false)
                .isDeleted(false)
                .isApproved(true)
                .lastOnlineTime(LocalDateTime.now())
                .messagesPermission(MessagesPermission.ALL).build());
        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> recoverPassword (String email) {
        if (!isEmailCorrect(email))
            return getIncorrectEmailResponse();

        Person currentPerson = personRepository.findByEmail(email);
        if (currentPerson == null)
            return new ResponseEntity<>(getErrorResponse(ErrorMessages.USER_NOTEXIST.getTitle()), HttpStatus.BAD_REQUEST);

        emailMessageService.sendMessage(email, SUBJECT, currentPerson.getConfirmationCode());

        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> setPassword(String token, String password) {
        if (token == null)
            return getUserInvalidResponse();

        Person currentUser = getCurrentUser();
        currentUser.setPassword(bcryptEncoder.encode(password));
        personRepository.save(currentUser);

        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> changeEmail(String email) {

        if (!isEmailCorrect(email))
            return getIncorrectEmailResponse();

        if (personRepository.findByEmail(email) != null)
            return new ResponseEntity<>(getErrorResponse(ErrorMessages.USER_EXISTS.getTitle()), HttpStatus.BAD_REQUEST);

        Person currentUser = getCurrentUser();
        currentUser.setEmail(email);
        personRepository.save(currentUser);

        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> editNotifications(String notificationType, boolean enable) {
        Person currentUser = getCurrentUser();
        if (currentUser == null) {
            return getUserInvalidResponse();
        }
        int notificationTypeId = notificationTypeRepository.findByName(notificationType).getId();
        NotificationSettings notificationSettings = NotificationSettings.builder()
                .personId(currentUser.getId()).notificationTypeId(notificationTypeId).enable(enable).build();
        notificationSettingsRepository.save(notificationSettings);
        return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    private boolean isEmailCorrect(String email) {
        return email.matches(".+@.+\\..{2,5}");
    }

    public Person getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();

        return personRepository.findByEmail(userDetails.getUsername());
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
