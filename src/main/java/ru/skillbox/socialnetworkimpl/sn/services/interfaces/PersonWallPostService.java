package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

import javax.servlet.http.HttpSession;

public interface PersonWallPostService {
    ResponseEntity<ResponsePlatformApi> getPersonsWallPostsByUserId(HttpSession session, long id, int offset, int itemPerPage);

    ResponseEntity<ResponsePlatformApi> addPostToUsersWall(HttpSession session, long id, int publishDate, PostRequestBody postRequestBody);
}
