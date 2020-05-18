package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT * FROM person"  +
            " WHERE first_name = :firstName AND last_name = :lastName" +
            " AND (CURRENT_DATE - birth_date) <= :ageTo" +
            " AND (CURRENT_DATE - birth_date) >= :ageFrom" +
            " AND city_id = :cityId" +
            " AND country_id = :countryId", nativeQuery = true)
    List<Person> findPersons(@Param("firstName") String firstName, @Param("lastName") String lastName,
                             @Param("ageTo") int ageTo, @Param("ageFrom") int ageFrom, @Param("countryId") int countryId,
                             @Param("cityId") int cityId, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE person SET is_blocked = 'true' WHERE id = :id", nativeQuery = true)
    void blockUserById(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE person SET is_blocked = 'false' WHERE id = :id", nativeQuery = true)
    void unBlockUserById(@Param("id") int id);
    //if is_blocked of type BIT -> CAST(0 AS BIT)
}