package ru.skillbox.socialnetworkimpl.sn.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.LikeRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.IsLikedResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.LikeUserListResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.LikesResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.LikeService;

import javax.servlet.http.HttpSession;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {
    @Override
    public ResponseEntity<ResponsePlatformApi<IsLikedResponse>> isLiked(HttpSession session, int userId, int itemId, String type) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<LikeUserListResponse>> getLikes(HttpSession session, int itemId, String type) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<LikeUserListResponse>> putLike(HttpSession session, LikeRequestBody likeRequestBody) {
        return null;
    }

    @Override
    public ResponseEntity<ResponsePlatformApi<LikesResponse>> deleteLike(HttpSession session, int itemId, String type) {
        return null;
    }
}
