package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.CommentRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostCommentResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.PostService;

import java.util.List;

/**
 * заглушка Post Service
 */
@Service
public class PostServiceImpl implements PostService {
    @Override
    public List<PostResponse> searchPublication(String text, Long dateFrom, Long dateTo, int offset, int itemPerPage) {
        return null;
    }

    @Override
    public PostResponse getPublication(int id) {
        return null;
    }

    @Override
    public List<PostCommentResponse> getComments(int id, int offset, int itemPerPage) {
        return null;
    }

    @Override
    public void deletePost(int id) {

    }

    @Override
    public void deleteComment(int id, int commentId) {

    }

    @Override
    public PostResponse editPost(int id, Long publishDate, PostRequest postRequest) {
        return null;
    }

    @Override
    public PostResponse recoverPost(int id) {
        return null;
    }

    @Override
    public PostCommentResponse editComment(int id, int commentId) {
        return null;
    }

    @Override
    public PostCommentResponse recoverComment(int id, int commentId) {
        return null;
    }

    @Override
    public PostCommentResponse createComment(int id, CommentRequest commentRequest) {
        return null;
    }

    @Override
    public void reportPost(int id) {

    }

    @Override
    public void reportComment(int id, int commentId) {

    }
}
