package ru.skillbox.socialnetworkimpl.sn.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmailMessageService emailMessageService;
    @Autowired
    private AccountServiceImpl accountService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<ResponsePlatformApi> getCurrentUser(HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> editCurrentUser(HttpSession session, PersonEditBody personEditBody) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> deleteCurrentUser(HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getUserById(HttpSession session, long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getPersonsWallPostsByUserId(HttpSession session, long id,
                                                                           int offset, int itemPerPage) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> addPostToUsersWall(HttpSession session, long id, int publishDate,
                                                                  PostRequestBody postRequestBody) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> searchPerson(HttpSession session, String firstName,
                                                            String lastName, int ageFrom, int ageTo, int countryId,
                                                            int cityId, int offset, int itemPerPage) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> blockUserById(HttpSession session, long id) {
        Query updatePasswordQuery = entityManager.createQuery("update Person p set p.isBlocked = 1 " +
                "where p.id = :id").setParameter("id", id);
        int result = updatePasswordQuery.executeUpdate();
        if (result != 1)
            return accountService.getInternalErrorResponse();

        // Заглушка на проверку пользователя
        boolean isAuthorized = true;
        if (!isAuthorized)
            return accountService.getUserInvalidResponse();
        return new ResponseEntity<>(accountService.getOkResponse(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> unblockUserById(HttpSession session, long id) {
        Query updatePasswordQuery = entityManager.createQuery("update Person p set p.isBlocked = 0 " +
                "where p.id = :id").setParameter("id", id);
        int result = updatePasswordQuery.executeUpdate();
        if (result != 1)
            return accountService.getInternalErrorResponse();

        // Заглушка на проверку пользователя
        boolean isAuthorized = true;
        if (!isAuthorized)
            return accountService.getUserInvalidResponse();
        return new ResponseEntity<>(accountService.getOkResponse(), HttpStatus.OK);
    }
}
