package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.City;

import java.util.List;
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    @Query(value = "SELECT * FROM cities WHERE city =:city", nativeQuery = true)
    City findCityByName(@Param("city") String city);
    @Query(value = "SELECT * FROM cities WHERE country_id = :countryId and city LIKE %:city% OFFSET :offset LIMIT :itemPerPage", nativeQuery = true)
    List<City> findCitiesByNameAndCountyById(@Param("countryId") int countryId, @Param("city") String language, @Param("offset") int offset, @Param("itemPerPage") int itemPerPage);
}
