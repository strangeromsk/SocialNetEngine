package ru.skillbox.socialnetworkimpl.sn.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;
import ru.skillbox.socialnetworkimpl.sn.domain.Post;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByAuthorId(int id);

    @Query(value = "SELECT p.* FROM post p " +
            "WHERE p.post_text LIKE %:text% AND p.time BETWEEN to_timestamp(:dateFrom/1000) AND to_timestamp(:dateTo/1000)", nativeQuery = true)
    List<Post> findAllByPostTextContainingAndTimeBetween(@Param("text") String text,@Param("dateFrom") long dateFrom,@Param("dateTo") long dateTo);

    @Query(value = "SELECT * FROM post p WHERE p.author_id IN (:ids) AND p.post_text LIKE %:text% AND p.time < CURRENT_TIMESTAMP ORDER BY p.time DESC LIMIT :itemPerPage OFFSET :offset", nativeQuery = true)
    List<Post> getFriendsPosts(@Param("ids") Collection <Integer> authorIds,
                               @Param("text") String text,
                               @Param("offset") int offset,
                               @Param("itemPerPage") int itemPerPage);
}
