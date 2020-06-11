package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetworkimpl.sn.domain.PostComment;

public interface CommentRepository extends JpaRepository<PostComment, Integer> {

}
