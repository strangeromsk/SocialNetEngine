package ru.skillbox.socialnetworkimpl.sn.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;
import ru.skillbox.socialnetworkimpl.sn.domain.Language;
import ru.skillbox.socialnetworkimpl.sn.repositories.CityRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.CountryRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.LanguageRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PlatformService {

    private final LanguageRepository languageRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public PlatformService(CityRepository cityRepository, CountryRepository countryRepository, LanguageRepository languageRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.languageRepository = languageRepository;
    }

    public List<Language> getLanguages(String language, int offset, int itemPerPage) {
        log.info("GetLanguages: language:'{}' offset:{} itemPerPage:{}", language, offset, itemPerPage);
        return languageRepository.findLanguageByName(language, offset, itemPerPage);
    }

    public List<City> getCities(int countryId, String city, int offset, int itemPerPage) {
        log.info("GetCities: country_id:'{}' city:'{}' offset:{} itemPerPage:{}", countryId, city, offset, itemPerPage);
        return cityRepository.findCitiesByNameAndCountyById(countryId, city, offset, itemPerPage);
    }

    public List<Country> getCountries(String country, int offset, int itemPerPage) {
        log.info("GetCountries: country:'{}' offset:{} itemPerPage:{}", country, offset, itemPerPage);
        return countryRepository.findCountryByName(country, offset, itemPerPage);
    }

}
