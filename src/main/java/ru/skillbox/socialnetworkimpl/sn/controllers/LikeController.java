package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.LikeRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.IsLikedResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.LikeUserListResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.likes.LikesResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.LikeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController (LikeService likeService) { this.likeService = likeService; }

    @GetMapping(value = "liked", params = {"user_id", "item_id", "type"})
    public ResponseEntity<ResponsePlatformApi<IsLikedResponse>> isLiked(HttpServletRequest request,
                                                                        @RequestParam("user_id") int userId,
                                                                        @RequestParam("item_id") int itemId,
                                                                        @RequestParam("type") String type) {
        return likeService.isLiked(request.getSession(), userId, itemId, type);
    }

    @GetMapping(value = "likes", params = {"item_id", "type"})
    public ResponseEntity<ResponsePlatformApi<LikeUserListResponse>> getLikes(HttpServletRequest request,
                                                                              @RequestParam("item_id") int itemId,
                                                                              @RequestParam("type") String type) {
        return likeService.getLikes(request.getSession(), itemId, type);
    }

    @PutMapping("likes")
    public ResponseEntity<ResponsePlatformApi<LikeUserListResponse>> putLike(HttpServletRequest request,
                                                       @RequestBody LikeRequestBody likeRequestBody) {
        return likeService.putLike(request.getSession(), likeRequestBody);
    }

    @DeleteMapping(value = "likes", params = {"item_id", "type"})
    public ResponseEntity<ResponsePlatformApi<LikesResponse>> deleteLike(HttpServletRequest request,
                                                                         @RequestParam("item_id") int itemId,
                                                                         @RequestParam("type") String type) {
        return likeService.deleteLike(request.getSession(), itemId, type);
    }
}