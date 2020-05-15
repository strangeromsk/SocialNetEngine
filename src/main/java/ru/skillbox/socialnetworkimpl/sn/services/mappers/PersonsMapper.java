package ru.skillbox.socialnetworkimpl.sn.services.mappers;

import org.mapstruct.Mapper;
import ru.skillbox.socialnetworkimpl.sn.api.requests.PersonEditBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.PersonResponse;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;

@Mapper(uses = {DataMapper.class})

public interface PersonsMapper {
    PersonResponse personToPersonResponse(Person person);
    Person requestPersonToPerson(PersonEditBody personEditBody);
}
