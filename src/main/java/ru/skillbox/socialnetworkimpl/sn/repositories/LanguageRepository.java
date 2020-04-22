package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.socialnetworkimpl.sn.domain.Language;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
    @Query(value = "SELECT * FROM language WHERE language LIKE %:language% OFFSET :offset LIMIT :itemPerPage", nativeQuery = true)
    List<Language> findLanguageByName(@Param("language") String language, @Param("offset") int offset, @Param("itemPerPage") int itemPerPage);
}
