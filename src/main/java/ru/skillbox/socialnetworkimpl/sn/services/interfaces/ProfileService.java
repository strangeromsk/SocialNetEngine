package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.MessageResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

import javax.servlet.http.HttpSession;

public interface ProfileService {
    ResponseEntity<ResponsePlatformApi<PersonResponse>> getCurrentUser(HttpSession session);

    ResponseEntity<ResponsePlatformApi<PersonResponse>> editCurrentUser(HttpSession session, PersonEditBody personEditBody);

    ResponseEntity<ResponsePlatformApi<MessageResponse>> deleteCurrentUser(HttpSession session);

    ResponseEntity<ResponsePlatformApi<PersonResponse>> getUserById(HttpSession session, long id);

    ResponseEntity<ResponsePlatformApi<PersonResponse>> searchPerson(HttpSession session, String firstName,
                                                                     String lastName, int ageFrom, int ageTo,
                                                                     int countryId, int cityId, int offset, int itemPerPage);

    ResponseEntity<ResponsePlatformApi<MessageResponse>> blockUserById(HttpSession session, long id);

    ResponseEntity<ResponsePlatformApi<MessageResponse>> unblockUserById(HttpSession session, long id);
}