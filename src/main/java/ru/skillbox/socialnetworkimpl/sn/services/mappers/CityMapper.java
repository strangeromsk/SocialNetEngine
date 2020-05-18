package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.mapstruct.Mapper;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CityResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import java.util.List;

@Mapper(uses = {DataMapper.class})
public interface CityMapper {
        CityResponse cityToCityResponse(City city);
        List<CityResponse>  cityToCityResponse(List<City> city);
}