package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByEmail(String email);

    @Query(value = "SELECT * FROM person WHERE id = :id", nativeQuery = true)
    Person findPersonById(@Param("id") long id);

    @Query(value = "SELECT * FROM person WHERE first_name LIKE %:firstName% AND last_name LIKE %:lastName%" +
            " AND DATE_DIFF(year, NOW(), birth_date) < :ageTo" +
            " AND DATE_DIFF(year, NOW(), birth_date) > :ageFrom" +
            " AND country_id = :countryId" +
            " AND city_id :cityId OFFSET :offset LIMIT :itemPerPage", nativeQuery = true)
    List<Person> findPersons(@Param("firstName") String firstName, @Param("lastName") String lastName,
                             @Param("ageTo") int ageTo, @Param("ageFrom") int ageFrom, @Param("countryId") int countryId,
                             @Param("cityId") int cityId, @Param("offset") int offset, @Param("itemPerPage") int itemPerPage);
//((date_part('year', CURRENT_DATE))-(date_part('year', birth_date)))
    @Modifying
    @Query(value = "UPDATE person SET is_blocked = CAST(1 AS BIT) WHERE id = :id", nativeQuery = true)
    void blockUserById(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE person SET is_blocked = CAST(0 AS BIT) WHERE id = :id", nativeQuery = true)
    void unBlockUserById(@Param("id") int id);
}