package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponseApi;

import javax.servlet.http.HttpSession;

public interface ProfileService {
    ResponseEntity<ResponseApi> getCurrentUser(HttpSession session);

    ResponseEntity<ResponseApi> editCurrentUser(HttpSession session, PersonEditBody personEditBody);

    ResponseEntity<ResponseApi> deleteCurrentUser(HttpSession session);

    ResponseEntity<ResponseApi> getUserById(HttpSession session, long id);

    ResponseEntity<ResponseApi> searchPerson(HttpSession session, String firstName,
                                             String lastName, int ageFrom, int ageTo,
                                             int countryId, int cityId, int offset, int itemPerPage);

    ResponseEntity<ResponseApi> blockUserById(HttpSession session, long id);

    ResponseEntity<ResponseApi> unblockUserById(HttpSession session, long id);
}