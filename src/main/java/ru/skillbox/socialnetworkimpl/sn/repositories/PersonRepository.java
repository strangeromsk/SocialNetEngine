package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PersonRepository extends CrudRepository<Person, Integer> {
    @Query(value = "SELECT * FROM person WHERE id = :id", nativeQuery = true)
    Person findPersonById(@Param("id") long id);

    @Query(value = "SELECT * FROM person WHERE first_name = :firstName AND last_name = :lastName" +
            "AND current_time-birth_date < :ageTo AND current_time-birth_date > :ageFrom AND country_id = :countryId" +
            "AND city_id = :cityId OFFSET :offset LIMIT :itemPerPage", nativeQuery = true)
    List<Person> findPersons(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("ageTo") int ageTo, @Param("ageFrom") int ageFrom, @Param("countryId") int countryId, @Param("cityId") int cityId, @Param("offset") int offset, @Param("itemPerPage") int itemPerPage);
}
