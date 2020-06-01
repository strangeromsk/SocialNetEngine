package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query(value = "SELECT * FROM countries WHERE country LIKE %:country% OFFSET :offset LIMIT :itemPerPage", nativeQuery = true)
    List<Country> findCountryByName(@Param("country") String country, @Param("offset") int offset, @Param("itemPerPage") int itemPerPage);
}
