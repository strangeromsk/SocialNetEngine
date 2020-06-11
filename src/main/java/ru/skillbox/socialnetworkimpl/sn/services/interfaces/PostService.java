package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import ru.skillbox.socialnetworkimpl.sn.api.requests.CommentRequest;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PostRequest;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostCommentResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PostResponse;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    // Поиск публикации
    List<PostResponse> searchPublication(String text, long dateFrom, long dateTo, int offset, int itemPerPage);
    // Получение публикации по ID
    PostResponse getPublication(int id);
    // Получение комментариев на публикации
    List<PostCommentResponse> getComments(int id, int offset, int itemPerPage);
    // Удаление публикации
    void deletePost(int id);
    // Удаление комментария к публикации
    void deleteComment(int id, int commentId);
    // Редактирование публикации
    PostResponse editPost(int id, Long publishDate, PostRequest postRequest);
    // Восстановление публикации по ID
    PostResponse recoverPost(int id);
    // Редактирование комментария к публикации
    PostCommentResponse editComment(int id, int commentId);
    // Восстановление комментария
    PostCommentResponse recoverComment(int id, int commentId);
    // Создание комментария к публикации
    PostCommentResponse createComment(int id, CommentRequest commentRequest);
    // Подать жалобу на публикацию
    void reportPost(int id);
    // Подать жалобу на комментарий к публикации
    void reportComment(int id, int commentId);
}
