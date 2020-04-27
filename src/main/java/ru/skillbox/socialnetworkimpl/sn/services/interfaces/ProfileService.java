package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

import javax.servlet.http.HttpSession;

public interface ProfileService {
    ResponseEntity<ResponsePlatformApi> getCurrentUser(HttpSession session);

    ResponseEntity<ResponsePlatformApi> editCurrentUser(HttpSession session, PersonEditBody personEditBody);

    ResponseEntity<ResponsePlatformApi> deleteCurrentUser(HttpSession session);

    ResponseEntity<ResponsePlatformApi> getUserById(HttpSession session, long id);

    ResponseEntity<ResponsePlatformApi> searchPerson(HttpSession session, String firstName,
                                                     String lastName, int ageFrom, int ageTo,
                                                     int countryId, int cityId, int offset, int itemPerPage);

    ResponseEntity<ResponsePlatformApi> blockUserById(HttpSession session, long id);

    ResponseEntity<ResponsePlatformApi> unblockUserById(HttpSession session, long id);
}