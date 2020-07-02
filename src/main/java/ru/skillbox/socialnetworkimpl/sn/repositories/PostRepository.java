package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByAuthorId(int id);

    @Query(value = "SELECT p.* FROM post p " +
            "WHERE p.post_text LIKE %:text% AND p.time BETWEEN to_timestamp(:dateFrom/1000) AND to_timestamp(:dateTo/1000)", nativeQuery = true)
    List<Post> findAllByPostTextContainingAndTimeBetween(@Param("text") String text,@Param("dateFrom") long dateFrom,@Param("dateTo") long dateTo);
}
