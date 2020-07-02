package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.socialnetworkimpl.sn.domain.Tag;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "SELECT t.* FROM tag t WHERE t.tag LIKE %:text% offset :offset limit :limit", nativeQuery = true)
    List<Tag> findAllByTagTextContaining(@Param("text") String text, @Param("offset") int offset, @Param("limit") int limit);
}
