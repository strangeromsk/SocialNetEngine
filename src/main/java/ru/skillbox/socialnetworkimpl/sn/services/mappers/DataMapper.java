package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.springframework.stereotype.Component;
import ru.skillbox.socialnetworkimpl.sn.api.responses.CityResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class DataMapper {
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

//    public static boolean asBlocked(Byte isBlocked) {
//        return isBlocked == 0 ? false : true;
//    }

    //TODO уберем!
    public CityResponse asCityResponse(String town) {
        return town != null ? new CityResponse(1,town) : null;
    }
}
