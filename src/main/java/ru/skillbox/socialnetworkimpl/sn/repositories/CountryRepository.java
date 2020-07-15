package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;

import java.util.List;
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query(value = "SELECT * FROM countries WHERE country =:country", nativeQuery = true)
    Country findCountryIdByName(@Param("country") String country);
    @Query(value = "SELECT * FROM countries WHERE country LIKE %:country% OFFSET :offset LIMIT :itemPerPage", nativeQuery = true)
    List<Country> findCountryByName(@Param("country") String country, @Param("offset") int offset, @Param("itemPerPage") int itemPerPage);
}
