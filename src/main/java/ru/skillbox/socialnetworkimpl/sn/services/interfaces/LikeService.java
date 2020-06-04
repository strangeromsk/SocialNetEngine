package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.LikeRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.IsLikedResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.LikeUserListResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.LikesResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

import javax.servlet.http.HttpSession;

public interface LikeService {
    ResponseEntity<ResponsePlatformApi<IsLikedResponse>> isLiked(HttpSession session, int userId, int itemId, String type);

    ResponseEntity<ResponsePlatformApi<LikeUserListResponse>> getLikes(HttpSession session, int itemId, String type);

    ResponseEntity<ResponsePlatformApi<LikeUserListResponse>> putLike(HttpSession session, LikeRequestBody likeRequestBody);

    ResponseEntity<ResponsePlatformApi<LikesResponse>> deleteLike(HttpSession session, int itemId, String type);
}
