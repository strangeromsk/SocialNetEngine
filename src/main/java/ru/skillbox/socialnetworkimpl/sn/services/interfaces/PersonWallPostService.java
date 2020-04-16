package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponseApi;

import javax.servlet.http.HttpSession;

public interface PersonWallPostService {
    ResponseEntity<ResponseApi> getPersonsWallPostsByUserId(HttpSession session, long id, int offset, int itemPerPage);

    ResponseEntity<ResponseApi> addPostToUsersWall(HttpSession session, long id, int publishDate, PostRequestBody postRequestBody);
}
