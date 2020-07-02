package ru.skillbox.socialnetworkimpl.sn.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CityResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import ru.skillbox.socialnetworkimpl.sn.domain.Country;
import ru.skillbox.socialnetworkimpl.sn.domain.Language;
import ru.skillbox.socialnetworkimpl.sn.services.PlatformService;
import ru.skillbox.socialnetworkimpl.sn.services.mappers.CityMapper;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/platform/")
public class PlatformController {


    private PlatformService platformService;
    @Autowired
    private CityMapper cityMapper;

    @Autowired
    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping("languages")
    public ResponsePlatformApi getLanguages(@RequestParam String language,
                                            @RequestParam(defaultValue = "0") int offset,
                                            @RequestParam(defaultValue = "20") int itemPerPage) {
        List<Language> listLanguage = platformService.getLanguages(language, offset, itemPerPage);
        int total = listLanguage.size();
        return new ResponsePlatformApi("done", total, offset, itemPerPage, listLanguage);
    }

    @GetMapping("countries")
    public ResponsePlatformApi getCountries(@RequestParam String country,
                                            @RequestParam(defaultValue = "0") int offset,
                                            @RequestParam(defaultValue = "20") int itemPerPage) {
        List<Country> listCountry = platformService.getCountries(country, offset, itemPerPage);
        int total = listCountry.size();
        return new ResponsePlatformApi("done", total, offset, itemPerPage, listCountry);
    }

    @GetMapping("cities")
    public ResponsePlatformApi getCities(@RequestParam int countryId,
                                         @RequestParam String city,
                                         @RequestParam(defaultValue = "0") int offset,
                                         @RequestParam(defaultValue = "20") int itemPerPage) {
        List<CityResponse> listCity = cityMapper.cityToCityResponse(platformService.getCities(countryId, city, offset, itemPerPage));
        int total = listCity.size();
        return new ResponsePlatformApi("done", total, offset, itemPerPage, listCity);
    }
}
