package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.PersonWallPostService;

import javax.servlet.http.HttpSession;

public class PersonWallPostServiceImpl implements PersonWallPostService {
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
}
