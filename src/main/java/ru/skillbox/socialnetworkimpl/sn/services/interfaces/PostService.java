package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostCommentRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CommentResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;

import java.util.List;

@Service
public interface PostService {
    // Поиск публикации
    List<PostResponse> searchPublication(String text, long dateFrom, long dateTo, int offset, int itemPerPage);
    // Получение публикации по ID
    PostResponse getPublication(int id);
    // Получение комментариев на публикации
    List<CommentResponse> getComments(int id, int offset, int itemPerPage);
    // Удаление публикации
    void deletePost(int id);
    // Удаление комментария к публикации
    void deleteComment(int id, int commentId);
    // Редактирование публикации
    PostResponse editPost(int id, Long publishDate, PostRequest postRequest);
    // Восстановление публикации по ID
    PostResponse recoverPost(int id);
    // Редактирование комментария к публикации
    CommentResponse editComment(int id, int commentId, PostCommentRequest commentRequest);
    // Восстановление комментария
    CommentResponse recoverComment(int id, int commentId);
    // Создание комментария к публикации
    CommentResponse createComment(int id, PostCommentRequest postCommentRequest);
    // Подать жалобу на публикацию
    void reportPost(int id);
    // Подать жалобу на комментарий к публикации
    void reportComment(int id, int commentId);
}
