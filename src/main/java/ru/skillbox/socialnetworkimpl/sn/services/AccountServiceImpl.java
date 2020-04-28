package ru.skillbox.socialnetworkimpl.sn.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessagesPermission;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.AccountService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

//TODO Запилить проверку e-mail регуляркой




@Component
public class AccountServiceImpl implements AccountService {
    private final String RECOVERY_MESSAGE = "This is a recovery message";
    private final String SUBJECT = "This is you password recovery link";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EmailMessageService emailMessageService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<ResponsePlatformApi> signUpAccount(String email, String passwd1, String passwd2, String firstName, String lastName, String code) {
        if (!passwd1.equals(passwd2) || !email.contains("@")) {
            return new ResponseEntity<>(getErrorResponse("Passwords does not matches or e-mail incorrect!")
                    , HttpStatus.BAD_REQUEST);
        }
        if (getPerson(email).size() > 0)
            return new ResponseEntity<>(getErrorResponse("User already exists!"), HttpStatus.BAD_REQUEST);
        personRepository.save(Person.builder().email(email).fistName(firstName).lastName(lastName)
                            .confirmationCode(code).password(passwd1).regDate(LocalDate.now())
                            .messagesPermission(MessagesPermission.ALL).build());
            return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> recoverPassword(String email) {
        if (!email.contains("@"))
            return new ResponseEntity<>(getErrorResponse("Incorrect e-mail"), HttpStatus.BAD_REQUEST);
        List<Person> currentPerson = getPerson(email);
        if (currentPerson.size() < 1)
            return new ResponseEntity<>(getErrorResponse("User does not exist!"), HttpStatus.BAD_REQUEST);
        if (currentPerson.size() != 1)
            return new ResponseEntity<>(getErrorResponse("Internal database error"),HttpStatus.OK);

        emailMessageService.sendMessage(email, SUBJECT, currentPerson.get(0).getConfirmationCode());
            return new ResponseEntity<>(getOkResponse(), HttpStatus.OK);
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


    private List<Person> getPerson(String email) {
        Query query = entityManager.createQuery("from Person p where email = :inEmail", Person.class);
        query.setParameter("inEmail", email);
        List<Person> currentPerson = query.getResultList();

        return currentPerson;

    }


}
