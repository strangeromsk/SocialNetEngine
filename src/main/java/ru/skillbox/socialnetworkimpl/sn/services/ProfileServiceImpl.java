package ru.skillbox.socialnetworkimpl.sn.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.MessageResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.ProfileService;

import javax.servlet.http.HttpSession;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public ResponseEntity<ResponsePlatformApi<PersonResponse>> getCurrentUser(HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<PersonResponse>> editCurrentUser(HttpSession session, PersonEditBody personEditBody) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<MessageResponse>> deleteCurrentUser(HttpSession session) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<PersonResponse>> getUserById(HttpSession session, long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<PersonResponse>> searchPerson(HttpSession session, String firstName,
                                                                            String lastName, int ageFrom, int ageTo, int countryId,
                                                                            int cityId, int offset, int itemPerPage) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<MessageResponse>> blockUserById(HttpSession session, long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<MessageResponse>> unblockUserById(HttpSession session, long id) {
        return null;
    }
}
