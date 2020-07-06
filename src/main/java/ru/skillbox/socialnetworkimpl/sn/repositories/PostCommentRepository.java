package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;
import ru.skillbox.socialnetworkimpl.sn.domain.PostComment;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    List<PostComment> findAllByPostId(Post postId);
}
