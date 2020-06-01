package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

import javax.servlet.http.HttpSession;

public interface ProfileService {
    ResponseEntity<ResponsePlatformApi> getCurrentUser(HttpSession session);

    ResponseEntity<ResponsePlatformApi> editCurrentUser(HttpSession session, PersonRequest personRequest);

    ResponseEntity<ResponsePlatformApi> deleteCurrentUser(HttpSession session);

    ResponseEntity<ResponsePlatformApi> getUserById(HttpSession session, int id);

    ResponseEntity<ResponsePlatformApi> getPersonsWallPostsByUserId(HttpSession session, int id, int offset, int itemPerPage);

    ResponseEntity<ResponsePlatformApi> addPostToUsersWall(HttpSession session, int id, long publishDate, PostRequest postRequest);

    ResponseEntity<ResponsePlatformApi> searchPerson(HttpSession session, String firstName,
                                                     String lastName, int ageFrom, int ageTo,
                                                     int countryId, int cityId, int offset, int itemPerPage);

    ResponseEntity<ResponsePlatformApi> blockUserById(HttpSession session, int id);

    ResponseEntity<ResponsePlatformApi> unblockUserById(HttpSession session, int id);
}