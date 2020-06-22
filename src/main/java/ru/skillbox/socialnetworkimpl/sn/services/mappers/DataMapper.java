package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CityResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.City;
import ru.skillbox.socialnetworkimpl.sn.repositories.CityRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class DataMapper {
    @Autowired
    private CityRepository cityRepository;

    public static long asEpochMillis(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay(ZoneOffset.UTC).toEpochSecond() * 1000 : 0;
    }

    public static long asEpochMillis(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.atOffset(ZoneOffset.UTC).toEpochSecond() * 1000 : 0;
    }

    public LocalDate asLocalDate(long epochMillis) {
        return LocalDate.ofEpochDay(epochMillis/86_400_000);
    }

    public LocalDateTime asLocalDateTime(long epochMillis) {
        return LocalDateTime.ofEpochSecond(epochMillis/1000, 0, ZoneOffset.UTC);
    }

}
